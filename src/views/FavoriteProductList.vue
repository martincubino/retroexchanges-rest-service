<template>
  <div class="mt-5">
    <b-alert :show="!!error" dismissible fade variant="danger">
      <p>{{ error }}</p>
    </b-alert>
    <div>
      <b-table striped hover :items="listItems" :fields="fields" :current-page="currentPage" :per-page="5">
        <template v-slot:cell(action)="data">
          <b-button variant="primary" class="mr-1" @click="showProduct(data)"> Detalle </b-button>
        </template>
        <template v-slot:cell(pictureList)="data">
          <img v-if="(data.item.pictureList.length>0)"
            :src="`data:image/png;base64,${data.item.pictureList[0].picture}`" width="auto" height="100" />
          <img v-else :src="`data:image/png;base64,${noimage}`" width="auto" height="70" />

        </template>
        <template v-slot:cell(category)="data">
          <span>{{ getCategoryName(data.item.category) }}</span>
        </template>
        <template v-slot:cell(createAt)="data">
          <span>{{ new Date(data.item.createAt).toLocaleString() }}</span>
        </template>
        <template v-slot:cell(updatedAt)="data">
          <span>{{ new Date(data.item.updatedAt).toLocaleString() }}</span>
        </template>
        <template v-slot:cell(price)="data">
          <span> {{data.item.price+'€'}}</span>
        </template>
        <template v-slot:cell(status)="data">
          <b-badge v-if="data.item.status=='AVAILABLE'" variant="success">{{getStatusLabel(data.item.status)}}</b-badge>
          <b-badge v-if="data.item.status=='RESERVED'" variant="warning">{{getStatusLabel(data.item.status)}}</b-badge>
          <b-badge v-if="data.item.status=='SOLD'" variant="secondary">{{getStatusLabel(data.item.status)}}</b-badge>
        </template>

      </b-table>
      <b-pagination v-model="currentPage" :total-rows="totalPages" :per-page="recordsPerPage">
      </b-pagination>
    </div>
  </div>
</template>

<script>

  export default {
    components: {
    },
    data() {
      return {
        email: '',
        isLoading: false,
        error: null,
        listItems: [],
        currentPage: 1,
        totalPages: 0,
        recordsPerPage: 5,
        noimage: "iVBORw0KGgoAAAANSUhEUgAAAEYAAAAnCAYAAACyhj57AAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsEAAA7BAbiRa+0AAAAiSURBVGhD7cExAQAAAMKg9U9tDB8gAAAAAAAAAAAAAHipASrPAAFA2GUnAAAAAElFTkSuQmCC",
        fields: [{
            key: "pictureList",
            label: "",
            sortable: false,
            image: true,
            class: "text-center",
            tdClass: "align-middle"
          },
          {
            key: "category",
            label: "Categoría",
            class: "text-left",
            sortable: true,
            sortDirection: "desc",
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
            key: "price",
            label: "Precio",
            class: "text-left",
            sortable: true,
            tdClass: "align-middle"
          },
          {
            key: "status",
            label: "Estado",
            class: "text-left",
            sortable: true,
            tdClass: "align-middle"
          },
          {
            key: "createAt",
            label: "Creado",
            class: "text-left",
            sortable: false,
            tdClass: "align-middle"
          },
          {
            key: "updatedAt",
            label: "Actualizado",
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
        productId: null,
        modalTitle: "Nuevo videojuego"
      }
    },
    computed: {
      getProductId() {
        return this.productId;
      },
      isLoggedIn() {
        return this.$store.getters.isAuthenticated;
      },
    },
    created() {
      if (this.isLoggedIn) {
        this.email = this.$store.getters.email;
        this.loadProducts();
      }
    },
    watch: {
      currentPage: {
        handler: function (value) {
          this.params = `page=${value}&size=${this.recordsPerPage}`;
          this.loadProducts();
        },
      },
    },
    methods: {
      getStatusLabel(data) {
        if (data == "AVAILABLE") {
          return "Disponible";
        }
        if (data == "RESERVED") {
          return "Reservado";
        }
        if (data == "SOLD") {
          return "Vendido";
        }
        return "";
      },
      async loadProducts() {
        this.isLoading = true;
        this.params = `page=${this.currentPage}&size=${this.recordsPerPage}`;
        try {
          await this.$store.dispatch('favorite/loadFavorites', {
            email: this.email
          });
          this.listItems=this.$store.getters['favorite/getFavorites'];
          this.totalPages = this.listItems.length;
          this.isLoading = false;
        } catch (error) {
          this.error = error.message || 'No se pudo cargar el listado de productos';
          this.isLoading = false;
        }
      },
      getCategoryName(data){
        console.log(data);
        return data.name;
      },
      showProduct(data) {
          let routeData = this.$router.resolve({name: 'productDetail', query: {product: data.item.productId}});
          window.open(routeData.href, '_blank');
      },
      handleError() {
        this.error = null;
      }
    }
  }
</script>