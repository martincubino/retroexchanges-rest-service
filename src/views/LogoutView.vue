<template>
    <b-overlay :show="isLoading" title="Authenticating..." rounded="sm">
        <main class="form-signin">
            <div class="card">
                <div class="card-body m-4 p-4">
                    <form @submit.prevent="submitForm">
                        <h2 class="h3 mb-3 fw-normal text-center">¿Desea cerrar la sesión?</h2>
                        <b-alert :show="!!error" dismissible fade variant="danger">
                            <p>{{ error }}</p>
                        </b-alert>
                        <br>
                        <b-row>
                            <b-col>
                                <!--<router-link class="w-100 btn btn-lg btn-primary" type="submit" to="/profile">Iniciar sesión-->
                                <b-button variant="primary" class="btn-lg" type="submit">Cerrar la sesión</b-button>
                            </b-col>
                            <b-col>
                                <b-button variant="secondary" class="btn-lg" :to="{name:'home'}" type="submit">
                                    Continuar autenticado
                                </b-button>
                            </b-col>
                        </b-row>
                    </form>
                </div>
            </div>
            <div>
                <b-modal id="modal-center" centered title="BootstrapVue">
                    <p class="my-4">Vertically centered modal!</p>
                </b-modal>
            </div>
        </main>
    </b-overlay>
</template>

<script>
    export default {

        data() {
            return {
                email: '',
                password: '',
                name: '',
                surname: '',
                formIsValid: true,
                mode: 'login',
                isLoading: false,
                error: null
            };
        },
        created() {
            this.$bvModal.hide('session-new-modal');
        },
        computed: {

            isLoggedIn() {
                return this.$store.getters.isAuthenticated;
            },
            loginMode() {
                if (this.mode === 'login') {
                    return false;
                } else {
                    return true;
                }
            },
        },
        methods: {
            async submitForm() {
                this.handleError();

                try {
                    await this.$store.dispatch('logout');
                    const redirectUrl = '/' + (this.$route.query.redirect || 'login');
                    this.$router.replace(redirectUrl);

                } catch (err) {
                    this.error = err.message;
                }
            },
            switchAuthMode() {
                if (this.mode === 'login') {
                    this.mode = 'signup';
                } else {
                    this.mode = 'login';
                }
            },
            handleError() {
                this.error = null;
            },
        },
    };
</script>

<style lang="css">
    body {

        align-items: center;
        background-color: #f6f6f6;
    }

    .form-signin {
        width: 100%;
        max-width: 450px;
        margin: auto;
    }

    label {
        font-weight: 600;
    }
</style>