<template>
  <div class="mt-5">
    <b-alert :show="!!error" dismissible fade variant="danger">
      <p>{{ error }}</p>
    </b-alert>
    <div>
      <b-table striped hover :items="listItems" :fields="fields" :current-page="currentPage" :per-page="5">
        <template v-slot:cell(action)="data">
          <b-button size="sm" class="mr-1" @click="edit(data)"> Bloquear </b-button>
        </template>
      </b-table>
      <b-pagination v-model="currentPage" :total-rows="totalPages" :per-page="recordsPerPage">
      </b-pagination>
    </div>
  </div>
</template>


<script>
  export default {
    components: {},
    data() {
      return {
        isLoading: false,
        error: null,
        listItems: [],
        currentPage: 1,
        totalPages: 0,
        recordsPerPage: 5,
        fields: [{
            key: "email",
            label: "Correo",
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
            key: "surname",
            label: "Apellidos",
            class: "text-left",
            sortable: true,
            image: true,
            tdClass: "align-middle"
          },
          {
            key: "address",
            label: "Dirección",
            class: "text-left",
            sortable: true,
            tdClass: "align-middle"
          },
          {
            key: "action",
            label: "",
            tdClass: "align-middle"
          },
        ],
        params: "",
      }
    },
    computed: {
      isLoggedIn() {
        return this.$store.getters.isAuthenticated;
      },
    },
    created() {
      if (this.$store.getters.isAdmin) {
        this.loadUsers();
      } else {
        const redirectUrl = '/' + (this.$route.query.redirect || 'login');
        this.$router.replace(redirectUrl);
      }
    },
    watch: {
      currentPage: {
        handler: function (value) {
          this.params = `page=${value}&size=${this.recordsPerPage}`;
          this.loadUsers();
        },
      },
    },
    methods: {
      async loadUsers() {
        this.isLoading = true;
         this.params = `page=${this.currentPage}&size=${this.recordsPerPage}`;
        try {
          await this.$store.dispatch('user/loadUsers');
          this.listItems = this.$store.getters['user/getUsers'];
          this.totalPages = this.listItems.length;
          this.isLoading = false;
        } catch (error) {
          this.error = error.message || 'No se pudo cargar el listado de usuarios';
          this.isLoading = false;
        }
      },
      handleError() {
        this.error = null;
      },
    }
  }
</script>