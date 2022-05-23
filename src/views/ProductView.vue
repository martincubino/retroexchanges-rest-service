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
                <b-row>
                    <b-col>
                        <b-form-group id="input-group-3" label="Imagenes:" label-for="input-3">
                            <b-card>
                                <div>
                                    <b-table ref="tableImages" thead-class="hidden_header" responsive small borderless
                                        :items="pictureList" :fields="fields">
                                        <template v-slot:cell(action)="data">
                                            <b-button v-if="data.item.picture" variant="primary" class="mr-1"
                                                @click="removeImage(data)">
                                                <font-awesome-icon icon="fa-solid fa-trash" size="xl" />&nbsp;
                                            </b-button>
                                        </template>
                                        <template v-slot:cell(picture)="data">
                                            <img v-if="data.item.picture"
                                                :src="`data:image/png;base64,${data.item.picture}`" width="120" height="auto" />
                                        </template>
                                    </b-table>
                                    <br>
                                    
                                    <b-form-file hidden size="sm" @change="onFileChange" browse-text="Buscar"
                                         placeholder="Seleccione una imagen" accept="image/jpg" v-model="file1" >
                                        <b-button size="sm" class="float-right" variant="secondary" @click="removeImage()">
                                        Añadir imagen</b-button>
                                        </b-form-file>
                                </div>

                            </b-card>
                            <template>
                                <b-badge v-if="this.status=='AVAILABLE'" variant="success">
                                    {{getStatusLabel(this.status)}}</b-badge>
                                <b-badge v-if="this.status=='RESERVED'" variant="warning">
                                    {{getStatusLabel(this.status)}}</b-badge>
                                <b-badge v-if="this.status=='SOLD'" variant="secondary">{{getStatusLabel(this.status)}}
                                </b-badge>
                            </template>
                            <br>
                        </b-form-group>
                    </b-col>
                    <b-col sm="7">
                        <b-form-group id="input-group-1" label="Nombre:" label-for="input-1">
                            <b-form-input id="input-1" v-model="name" type="text"
                                placeholder="Introduzca el nombre del videojuego o consola" required>
                            </b-form-input>
                        </b-form-group>
                        <b-form-group id="input-group-2" label="Descripción:" label-for="input-2">
                            <b-form-textarea rows="3" max-rows="5" id="input-2" v-model="description" type="text"
                                placeholder="Introduzca la descripción del videojuego o consola" required>
                            </b-form-textarea>
                        </b-form-group>
                        <div>Categoría:</div>
                        <b-form-select class="mt-2" id="input-4" v-model="categoryId" :options="categories"
                            type="select" text-field="name" value-field="categoryId" required></b-form-select>

                        <b-form-group class="mt-2" id="input-group-5" label="Precio:" label-for="input-5">
                            <b-form-input id="input-5" v-model="price" type="number" placeholder="Introduzca el precio"
                                required>
                            </b-form-input>
                        </b-form-group>
                    </b-col>
                </b-row>

                <div slot="modal-footer" class="w-100">
                    <br>
                    <p class="float-left"></p>
                    <b-button size="mt-2 md mr-2" class="float-right" variant="primary" @click="onSubmit">
                        Guardar
                    </b-button>
                </div>

            </b-form>
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            id: Number,
            new: Boolean
        },
        components: {},
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
                categoryId: null,
                imageUploaded: null,
                formIsValid: true,
                files: [],
                pictureList: [],
                slide: 0,
                sliding: null,
                file1: null
            }
        },
        computed: {
            isLoggedIn() {
                return this.$store.getters.isAuthenticated;
            },
        },
        created() {
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
                let product = {
                    name: this.name,
                    description: this.description,
                    owner: this.owner,
                    price: this.price,
                    category: this.categories.find(x => x.categoryId === this.categoryId),
                    pictureList: this.pictureList,
                    status: this.status
                }
                if (this.new == false) {
                    try {
                        product.productId = this.productId;
                        await this.$store.dispatch('product/updateProduct', product);
                        //event.preventDefault()
                        this.showAlert();
                    } catch (error) {
                        this.error = error.message || 'No se pudo actualizar la información de la product';
                    }
                } else {
                    product.status = "AVAILABLE";
                    try {
                        await this.$store.dispatch('product/createProduct', product);
                        //event.preventDefault()
                        this.showAlert();
                    } catch (error) {
                        this.error = error.message || 'No se pudo recuperar la información de la categoria';
                    }
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
            onFileChange(e) {
                var files = e.target.files || e.dataTransfer.files;
                if (!files.length)
                    return;
                this.file1 = files[0];
                this.createImage(files[0],(image=>{
                    let picture = {
                        new: true,
                        picture: image,
                        productId: this.productId
                    }
                    if (typeof this.pictureList=="undefined"){
                        this.pictureList = [];
                    }
                    this.pictureList.push(picture);
                    this.$refs.tableImages.refresh();
                }));
                
            },
            createImage(file,callback) {
                var reader = new FileReader();
                var vm = this;

                reader.onload = (e) => {
                    vm.image = e.target.result;
                    console.log(this.image);
                    let imageURI = this.image.split(',');
                    vm.image = imageURI[1];
                    callback(vm.image);

                };
                reader.readAsDataURL(file);
            },
            async removeImage(data) {
                this.pictureList[data.index].picture = '';
                this.$refs.tableImages.refresh();
            },
            onSlideStart(slide) {
                this.slide = slide;
            },
            onSlideEnd(slide) {
                this.slide = slide;
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