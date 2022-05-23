<template>
    <b-overlay :show="isLoading" title="Authenticating..." rounded="sm">
        <main class="form-signin">
            <div class="card">
                <div class="card-body m-4 p-4">
                    <form @submit.prevent="submitForm">
                        <h2 class="h3 mb-3 fw-normal text-center">{{submitButtonCaption}}</h2>
                        <b-alert :show="!!error" dismissible fade variant="danger">
                            <p>{{ error }}</p>
                        </b-alert>
                        <div class="form-group">
                            <label>Dirección de correo</label>
                            <input required type="email" class="form-control form-control-lg" placeholder="email"
                                v-model.trim="email" />
                        </div>
                        <div class="form-group">
                            <label>Contraseña</label>
                            <input required type="password" class="form-control form-control-lg"
                                placeholder="contraseña" v-model.trim="password" />
                        </div>
                        <div v-if="loginMode" class="form-group">
                            <label>Nombre</label>
                            <input required type="text" class="form-control form-control-lg" placeholder="Nombre"
                                v-model.trim="name" />
                        </div>
                        <div v-if="loginMode" class="form-group">
                            <label>Apellidos</label>
                            <input required type="text" class="form-control form-control-lg" placeholder="Apellidos"
                                v-model.trim="surname" />
                        </div>
                        <b-row>
                            <p v-if="!formIsValid">Introduzca un correo electronico y contraseña válidos (contraseña
                                mayor
                                de 6 carácteres)</p>
                            <b-col>
                                <!--<router-link class="w-100 btn btn-lg btn-primary" type="submit" to="/profile">Iniciar sesión-->
                                <b-button class="btn-primary btn-lg" type="submit">{{submitButtonCaption}}</b-button>
                            </b-col>
                            <b-col>
                                <b-button class="btn-secondary btn-lg" @click="switchAuthMode" type="submit">
                                    {{switchModeCaption}}
                                </b-button>
                            </b-col>
                        </b-row>
                    </form>
                </div>
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
            submitButtonCaption() {
                if (this.mode === 'login') {
                    return 'Iniciar sesión';
                } else {
                    return 'Registrarse';
                }
            },
            switchModeCaption() {
                if (this.mode === 'login') {
                    return 'Registrarse';
                } else {
                    return 'Iniciar sesión';
                }
            }
        },
        methods: {
            async submitForm() {
                this.handleError();
                this.formIsValid = true;
                if (
                    this.email === '' ||
                    !this.email.includes('@') ||
                    this.password.length < 6
                ) {
                    this.formIsValid = false;
                    return;
                }

                this.isLoading = true;

                const actionPayload = {
                    email: this.email,
                    password: this.password,
                    name: this.name,
                    surname: this.surname
                };

                try {
                    if (this.mode === 'login') {
                        await this.$store.dispatch('login', actionPayload);
                    } else {
                        await this.$store.dispatch('signup', actionPayload);
                    }
                    const redirectUrl = '/' + (this.$route.query.redirect || 'profile');
                    this.$router.replace(redirectUrl);
                } catch (err) {
                    this.error = err.message;
                }

                this.isLoading = false;
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