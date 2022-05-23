<template>
    <div class="form-group">
        <div class="ml-5 ">
            <b-form @submit="onSubmit">
                <div v-if="isLoading">
                    <b-spinner>Cargando...</b-spinner>
                </div>
                <b-alert :show="!!error" dismissible fade variant="danger">
                    <p>{{ error }}</p>
                </b-alert>
                <b-alert :show="!formIsValid" dismissible fade variant="danger">
                    <p>{{formFormatWarning}}</p>
                </b-alert>

                <b-alert :show="dismissCountDown" dismissible variant="success" @dismissed="dismissCountDown=0"
                    @dismiss-count-down="countDownChanged">
                    <p>Los datos se guardaron correctamente</p>
                    <b-progress variant="success" :max="dismissSecs" :value="dismissCountDown" height="4px">
                    </b-progress>
                </b-alert>
                <h5> {{product.name}}</h5>
                <p> {{product.description}}</p>
                <h6>Puesto en venta por {{product.owner}}</h6>
                <h6>Al precio de {{product.price+'€'}}</h6>


                <b-badge v-if="product.status=='AVAILABLE'" variant="success">
                    Disponible
                </b-badge>
                <b-badge v-if="product.status=='RESERVED'" variant="warning">
                    Reservado
                </b-badge>
                <b-badge v-if="product.status=='SOLD'" variant="secondary">
                    Vendido
                </b-badge>
                <b-form-group class="mt-2" id="input-group-5" label="Hacer oferta:" label-for="input-5">
                    <b-form-input id="input-5" v-model="price" type="number" placeholder="Introduzca el precio"
                        required>
                    </b-form-input>
                </b-form-group>

                <div slot="modal-footer" class="w-100">
                    <br>
                    <p class="float-left"></p>
                    <b-button  size="mt-2 md mr-2" class="float-right" variant="primary" @click="onSubmit">
                        Enviar
                    </b-button>
                </div>

            </b-form>
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            parent: Object,
            product: Object,
            seller: String,
            buyer: String,
            new: Boolean
        },
        components: {},
        data() {
            return {
                isLoading: false,
                dismissSecs: 5,
                dismissCountDown: 0,
                showDismissibleAlert: false,
                formFormatWarning: null,
                categories: [],
                error: null,
                name: null,
                description: null,
                price: null,
                owner: null,
                image: null,
                category: null,
                categoryId: null,
                imageUploaded: null,
                formIsValid: true,
            }
        },
        computed: {
            isLoggedIn() {
                return this.$store.getters.isAuthenticated;
            },
        },
        created() {
            this.price = this.product.price;
            this.email = this.$store.getters.email;
            console.log(this.product);
            if (this.$store.getters.isAuthenticated) {
                this.categories = this.$store.getters['category/getCategories'];
                this.owner = this.$store.getters.email;
                if (this.new == false) {
                    this.loadProduct();
                }
            } else {
                const redirectUrl = '/' + (this.$route.query.redirect || 'login');
                this.$router.replace(redirectUrl);
            }
        },
        methods: {
            countDownChanged(dismissCountDown) {
                this.dismissCountDown = dismissCountDown
            },
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
            showAlert() {
                this.dismissCountDown = this.dismissSecs
            },
            goHome() {
                const redirectUrl = '/';
                this.$router.replace(redirectUrl);
            },
            async onSubmit() {
                this.handleError();
                

                let buyrequest = {
                    buyer: this.email,
                    seller: this.product.owner,
                    productId: this.product.productId,
                    price: this.price,
                    status: this.status
                };
                try {
                    await this.$store.dispatch('buyrequest/createBuyRequest', buyrequest);
                    this.showAlert();
                    parent.$refs.modalBuyRequest.hide();
                } catch (error) {
                    this.error = error.message || 'No se pudo actualizar la información de la product';
                }
                
            },
            async loadProduct() {
                this.isLoading = true;
                try {
                    await this.$store.dispatch('product/loadProduct', this.id);
                } catch (error) {
                    this.error = error.message || 'No se pudo recuperar la información del videojuego';
                }
                this.isLoading = false;
                let product = this.$store.getters['product/getProduct'];
                this.name = product.name;
                this.description = product.description;
                this.createAt = product.createAt;
                this.updatedAt = product.updatedAt;
                this.productId = product.productId;
                this.owner = product.owner;
                this.price = product.price;
                this.category = product.category;
                this.categoryId = this.category.categoryId;
                this.status = product.status;
                this.pictureList = product.pictureList;
                console.log(this.pictureList);

            },
            handleError() {
                this.error = null;
            },
        }
    }
</script>
<style scoped>
    @media (min-width: 500) {
        .modal .modal-huge {
            max-width: 90% !important;
            width: 90% !important;
            ;
        }
    }

    .hidden_header {
        display: none;
    }
</style>