<template>
    <div>
        <b-col>
            <b-container align="center" class="p-3 carrousel">
                <b-carousel style="text-shadow: 1px 1px 2px #333;" v-model="slide" id="carousel" :no-wrap="false"
                    :controls="true" :interval="5000" :indicators=true>

                    <b-carousel-slide v-for="item in pictureList" :key="item.picture" class=".img_slide"
                        :img-src="`data:image/png;base64,${item.picture}`">
                    </b-carousel-slide>
                </b-carousel>

            </b-container>
        </b-col>
        <b-container>
            <div v-if="isLoading">
                <b-spinner>Cargando...</b-spinner>
            </div>
            <br><br>

            <b-card class="md">
                <b-col cols=8>
                    <b-badge v-if="owner == email " variant="warning">
                        Eres el propietario
                    </b-badge>
                    <div v-if="owner != email ">
                        <b-form-checkbox v-if="isLoggedIn" @input="setFavorite" v-model="favorite" switch size="lg">
                            <p v-if="favorite" style="color:red">
                                <font-awesome-icon icon="fa-solid fa-star" />
                                En mis favoritos
                            </p>
                            <p v-else style="color:gray">
                                <font-awesome-icon icon="fa-solid fa-star" />
                            </p>

                        </b-form-checkbox>
                    </div>
                    <p v-if="owner != email ">
                        <b-badge v-if="owner != email " variant="info">
                            Puesto en venta por:
                        </b-badge>
                    </p>
                    <p v-if="rating>-1">
                            {{owner}} ({{rating}}
                        <font-awesome-icon style="color:blue" icon="fa-solid fa-thumbs-up" />&nbsp;)
                        </p>
                    <br>
                    <h5>{{price+'€'}}</h5>
                    <h6> {{name}}</h6>
                    <p> {{description}}</p>
                    <b-badge v-if="status=='AVAILABLE'" variant="success">
                        Disponible
                    </b-badge>
                    <b-badge v-if="status=='RESERVED'" variant="warning">
                        Reservado. No puedes solicitar su compra
                    </b-badge>
                    <p v-if="owner != email ">
                    <b-badge v-if="status=='SOLD'" variant="secondary">
                        Vendido. No puedes solicitar su compra
                    </b-badge>
                    </p>
                    
                </b-col>
                <div v-if="status=='AVAILABLE'">
                    <b-button v-if="isLoggedIn" class="mt-4 ml-3" variant="primary" @click="newBuyRequest(data)">
                        Solicitar compra</b-button>
                </div>
                <div v-else>
                    <b-button v-if="isLoggedIn" class="mt-4 ml-3" variant="primary" @click="goHome()">
                        Seguir buscando</b-button>


                </div>


            </b-card>
        </b-container>
        <b-modal size="lg" centered ref="modalBuyRequest" v-bind:title=this.modalTitle hide-footer>
            <BuyRequestView :parent="this.parent" :buyer="this.email" :seller="this.owner" :product="this.product"
                :new="(this.modalTitle=='Nueva solicitud de compra')" />
        </b-modal>
    </div>
</template>

<script>
    import BuyRequestView from '@/views/BuyRequestView.vue'
    export default {
        components: {
            BuyRequestView
        },
        data() {
            return {
                fields: [{
                        key: "picture",
                        label: "",
                        sortable: false,
                        image: true,
                        class: "text-center",
                        tdClass: "align-middle"
                    },
                    {
                        key: "action",
                        label: "",
                        class: "text-center",
                        tdClass: "align-middle"
                    },
                ],
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
                categoryName: null,
                categoryId: null,
                imageUploaded: null,
                formIsValid: true,
                files: [],
                pictureList: [],
                slide: 0,
                sliding: null,
                file1: null,
                status: null,
                favorite: false,
                email: null,
                modalTitle: null,
                product: null,
                parent: this,
                rating: null

            }
        },
        computed: {
            isLoggedIn() {
                return this.$store.getters.isAuthenticated;
            },
        },
        created() {
            this.id = this.$route.query.product;
            this.categories = this.$store.getters['category/getCategories'];
            this.email = this.$store.getters.email;
            this.loadProduct();

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
            async loadProduct() {
                this.isLoading = true;
                try {
                    await this.$store.dispatch('product/loadProduct', this.id);
                } catch (error) {
                    this.error = error.message || 'No se pudo recuperar la información del videojuego';
                }
                this.isLoading = false;
                let product = this.$store.getters['product/getProduct'];
                this.product = product;
                this.name = product.name;
                this.description = product.description;
                this.createAt = product.createAt;
                this.updatedAt = product.updatedAt;
                this.productId = product.productId;
                this.owner = product.owner;
                this.price = product.price;
                this.category = product.category;
                this.categoryId = product.category.categoryId;
                this.categoyName = product.category.name;
                this.status = product.status;
                this.pictureList = product.pictureList;
                this.loadFavorite();

            },
            async loadFavorite() {
                this.isLoading = true;
                let favorite;
                let fav = {
                    productId: this.productId,
                    email: this.email
                }
                try {
                    await this.$store.dispatch('favorite/loadFavorite', fav);
                    favorite = this.$store.getters['favorite/getFavorite'];
                } catch (error) {
                    console.log("No es favorito de " + this.email);
                }
                try {
                    await this.$store.dispatch('favorite/loadFavorites', fav);
                } catch (error) {
                    console.log("No es favorito de " + this.email);
                }
                this.isLoading = false;
                if (typeof favorite != "undefined") {
                    this.favorite = true;
                } else {
                    this.favorite = false;
                }
                this.loadRating();

            },
            async loadRating() {
                this.isLoading = true;
                try {
                    await this.$store.dispatch('rating/loadRating', this.owner);
                } catch (error) {
                    this.error = error.message || 'No se pudo recuperar la reputacion del vendedor';
                }
                this.isLoading = false;
                let rating = this.$store.getters['rating/getRating'];
                if ((typeof rating != "undefined") && (rating != null)) {
                    this.owner = rating.email;
                    this.rating = rating.rating;
                }
            },
            async setFavorite(state) {
                let favorite = {
                    productId: this.productId,
                    email: this.email
                }
                if (state == true) {
                    try {
                        await this.$store.dispatch('favorite/createFavorite', favorite);
                    } catch (error) {
                        console.log(error);
                    }
                } else {
                    try {
                        await this.$store.dispatch('favorite/deleteFavorite', favorite);
                    } catch (error) {
                        console.log(error);
                    }
                }
            },
            newBuyRequest() {
                this.productId = 0;
                this.parent = this;
                this.modalTitle = "Nueva solicitud de compra";
                this.$refs.modalBuyRequest.show();
            },
            handleError() {
                this.error = null;
            }
        }
    }
</script>
<style>
    .carousel-inner>.item>img {
        width: 640px;
        height: 360px;
    }

    .carousel {
        width: 640px;
        height: 360px;
    }
</style>