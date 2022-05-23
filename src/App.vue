<template>
  <div id="app">
    <b-navbar class="shadow" toggleable="sm" type="dark" variant="info">
      <b-navbar-brand :to="{name:'home'}" exact>
        <img src="@/assets/images/logo.png" alt="RetroExchanges">
      </b-navbar-brand>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <!--<b-navbar-nav>
          <b-nav-item :to="{name:'home'}" exact>Inicio</b-nav-item>
          <b-nav-item :to="{name:'about'}">Acerca de...</b-nav-item>
        </b-navbar-nav>-->
        <b-navbar-nav class="md-auto">

        </b-navbar-nav>
        <b-navbar-nav small class="ml-auto">
          <b-nav-form action="">
            <b-input-group class="text-center shadow-lg" >
              
              <!--DESACTIVADA BARRA DE BUSQUEDA
              <b-input-group-prepend is-text>
                <b-icon icon="search"></b-icon>
              </b-input-group-prepend>
              <b-form-input  v-model="searchText" placeholder="¿Qué estas buscando?"></b-form-input>-->
            </b-input-group>
            <b-nav-item class="text-center" :to="{name:'profile'}" exact>
              <p></p>
              <p v-if="isLoggedIn">
                <b-icon icon="person"></b-icon><br>{{getUserEmail}}
              </p>
            </b-nav-item>
            <b-nav-item class="text-center" v-if="isLoggedIn" :to="{name:'logout'}" exact>
              <p></p>
              <p v-if="isLoggedIn">
                <b-icon icon="box-arrow-right"></b-icon><br>{{initSesionCaption}}
              </p>
              <p v-else>
                <b-icon icon="person"></b-icon><br>{{initSesionCaption}}
              </p>
            </b-nav-item>
            <b-nav-item class="text-center" v-if="isLoggedIn == false" :to="{name:'login'}" exact>
              <p></p>
              <p v-if="isLoggedIn">
                <b-icon icon="box-arrow-right"></b-icon><br>{{initSesionCaption}}
              </p>
              <p v-else>
                <b-icon icon="person"></b-icon><br>{{initSesionCaption}}
              </p>
            </b-nav-item>
          </b-nav-form>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <router-view />
  </div>
</template>
<script>
  export default {
    name: 'RetroExchanges',
    components: {},
    data() {
      return {
        error: null,
        searchText: null
      }
    },
    computed: {
      isLoggedIn() {
        return this.$store.getters.isAuthenticated;
      },
      getUserEmail() {
        return this.$store.getters.email;
      },
      initSesionCaption() {
        if (this.isLoggedIn === false) {
          return 'Iniciar sesión';
        } else {
          return 'Cerrar sesión';
        }
      },
      methods: {
        async onSubmit() {
          console.log(this.searchText);
        }
      }
    }
  }
</script>