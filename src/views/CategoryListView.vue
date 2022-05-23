<template>
  <div class="mt-5">
    <b-alert :show="!!error" dismissible fade variant="danger">
      <p>{{ error }}</p>
    </b-alert>
    <div>
      <b-table striped hover :items="listItems" :fields="fields" :current-page="currentPage" :per-page="5">
        <template v-slot:cell(action)="data">
          <b-button variant="primary" class="mr-1" @click="editCategory(data)"> Editar </b-button>
        </template>
        <template v-slot:cell(image)="data">
          <img v-if="data.item.image" :src="`data:image/png;base64,${data.item.image}`" class="center" width="auto"
            height="70" />
        </template>
        <template v-slot:cell(createAt)="data">
          <span>{{ new Date(data.item.createAt).toLocaleString() }}</span>
        </template>
        <template v-slot:cell(updatedAt)="data">
          <span>{{ new Date(data.item.updatedAt).toLocaleString() }}</span>
        </template>
      </b-table>
      <b-pagination v-model="currentPage" :total-rows="totalPages" :per-page="recordsPerPage">
      </b-pagination>
    </div>
    <br>
    <p align="left">
      <b-button class="aling-left" variant="outline-primary" @click="newCategory(data)">Nueva categoría</b-button>
    </p>
    <b-modal size="lg" @hide="loadCategories" centered ref="modalCategory" v-bind:title=this.modalTitle hide-footer>
      <CategoryView :id="this.categoryId" :new="(this.modalTitle=='Nueva categoría')" />
    </b-modal>
  </div>
</template>

<script>
  import CategoryView from '@/views/CategoryView.vue'
  export default {
    components: {
      CategoryView
    },
    data() {
      return {
        isLoading: false,
        error: null,
        listItems: [],
        currentPage: 1,
        totalPages: 0,
        recordsPerPage: 5,
        fields: [{
            key: "image",
            label: "",
            sortable: false,
            class: "text-center",
            image: true,
            tdClass: "align-middle"
          },
          {
            key: "name",
            label: "Nombre",
            sortable: true,
            class: "text-left",
            tdClass: "align-middle"
          },
          {
            key: "description",
            label: "Descripción",
            class: "text-left",
            sortable: true,
            tdClass: "align-middle"
          },
          {
            key: "createAt",
            label: "Creada",
            class: "text-left",
            sortable: false,
            tdClass: "align-middle"
          },
          {
            key: "updatedAt",
            label: "Actualizada",
            class: "text-left",
            sortable: false,
            tdClass: "align-middle"
          },
          {
            key: "action",
            label: "",
            tdClass: "align-middle"
          },
        ],
        params: "",
        categoryId: null,
        modalTitle: "Nueva categoría"
      }
    },
    computed: {
      getCategoryId() {
        return this.categoryId;
      },
      isLoggedIn() {
        return this.$store.getters.isAuthenticated;
      },
    },
    created() {
      if (!this.$store.getters.isAdmin) {
        const redirectUrl = '/' + (this.$route.query.redirect || 'login');
        this.$router.replace(redirectUrl);
      }
      this.loadCategories();
    },
    watch: {
      currentPage: {
        handler: function (value) {
          this.params = `page=${value}&size=${this.recordsPerPage}`;
          this.loadCategories();
        },
      },
    },
    methods: {
      async loadCategories() {
        this.isLoading = true;
        this.params = `page=${this.currentPage}&size=${this.recordsPerPage}`;
        try {
          await this.$store.dispatch('category/loadCategories');
          this.listItems = this.$store.getters['category/getCategories'];
          this.totalPages = this.listItems.length;
          this.isLoading = false;
        } catch (error) {
          this.error = error.message || 'No se pudo cargar el listado de categorias';
          this.isLoading = false;
        }
      },
      deleteRecord(data) {
        this.$bvModal
          .msgBoxConfirm("Are you sure wants to delete?", {
            title: "Please Confirm",
            size: "mm",
            buttonSize: "sm",
            okVariant: "danger",
            okTitle: "YES",
            cancelTitle: "NO",
            footerClass: "p-2",
            hideHeaderClose: false,
            centered: true,
          })
          .then((value) => {
            if (value) {
              this.listItems.splice(data.index, 1);
            }
          });
      },
      editCategory(data) {
        this.categoryId = data.item.categoryId;
        this.modalTitle = "Editar categoría";
        console.log(this.categoryId);
        this.$refs.modalCategory.show();
      },
      newCategory() {
        this.categoryId = 0;
        this.modalTitle = "Nueva categoría";
        console.log(this.categoryId);
        this.$refs.modalCategory.show();
      },
      handleError() {
        this.error = null;
      }
    }
  }
</script>