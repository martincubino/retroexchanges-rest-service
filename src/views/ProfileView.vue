<template>
    <div class="profile">
        <b-card>
            <b-tabs @activate-tab="afterTabSelection()" pills vertical>
                <b-tab active>
                    <template #title>
                        <div class="text-center">
                            <p></p>
                            <font-awesome-icon icon="fa-solid fa-id-card" size="2x" />
                            <p>Mis datos</p>
                        </div>
                    </template>
                    <div>
                        <h1>Mis datos</h1>
                        <UserDataView ref="userDataView" />
                    </div>
                </b-tab>

                <b-tab>
                    <template #title>
                        <div class="text-center">
                            <p></p>
                            <font-awesome-icon icon="fa-solid fa-ghost" size="2x" />
                            <p>Mis videojuegos</p>
                        </div>
                    </template>
                    <h1>Mis videojuegos</h1>
                    <ProductListView />
                </b-tab>
                <b-tab>
                    <template #title>
                        <div class="text-center">
                            <p></p>
                            <font-awesome-icon icon="fa-solid fa-envelopes-bulk" size="2x" />
                            <p>Solicitudes de compra</p>
                        </div>
                    </template>
                    <h1>Solicitudes de compra</h1>
                    <BuyRequestListView />
                </b-tab>
                <b-tab>
                    <template #title>
                        <div class="text-center">
                            <p></p>
                            <font-awesome-icon icon="fa-solid fa-star" size="2x" />
                            <p>Favoritos</p>
                        </div>
                    </template>
                    <h1>Favoritos</h1>
                    <FavoriteProductList />
                </b-tab>
                <b-tab>
                    <template #title>
                        <div class="text-center">
                            <p></p>
                            <font-awesome-icon icon="fa-solid fa-thumbs-up" size="xl" />&nbsp;
                            <font-awesome-icon icon="fa-solid fa-thumbs-down" size="xl" />
                            <p>Valoraciones</p>
                        </div>
                    </template>
                    <h1>Valoraciones</h1>
                </b-tab>
                <b-tab v-if="isAdmin">
                    <template #title>
                        <div class="text-center">
                            <p></p>
                            <font-awesome-icon icon="fa-solid fa-list-check" size="2x" />
                            <p>Categorías</p>
                        </div>
                    </template>
                    <h1>Categorías</h1>
                    <CategoryListView />
                </b-tab>
                <b-tab v-if="isAdmin">
                    <template #title>
                        <div class="text-center">
                            <p></p>
                            <font-awesome-icon icon="fa-solid fa-users" size="2x" />
                            <p>Usuarios</p>
                        </div>
                    </template>
                    <h1>Usuarios</h1>
                    <UserListView />
                </b-tab>
            </b-tabs>
        </b-card>
    </div>
</template>

<script>
    import UserDataView from '@/views/UserDataView.vue'
    import UserListView from '@/views/UserListView.vue'
    import CategoryListView from '@/views/CategoryListView.vue'
    import ProductListView from '@/views/ProductListView.vue'
    import FavoriteProductList from '@/views/FavoriteProductList.vue'
    import BuyRequestListView from '@/views/BuyRequestListView.vue'
    
    export default {
        name: 'RetroExchanges',
        components: {
            UserDataView,
            UserListView,
            CategoryListView,
            ProductListView,
            FavoriteProductList,
            BuyRequestListView
        },
        data() {
            return {
                isAdmin: false
            }
        },
        created() {
            this.isAdmin = this.$store.getters.isAdmin;
        },
        methods: {
             afterTabSelection: function() {
            },
            handleError() {
                this.error = null;
            }
        }
    }
</script>