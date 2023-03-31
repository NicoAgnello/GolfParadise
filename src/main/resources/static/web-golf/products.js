const { createApp } = Vue

createApp({

    data() {
        return {
            products:           [],
            productDetail:      {},
            categories:         [],
            filteredProducts:    [],
            cart:               [],
            categoryToFilter:   "",
            searchValue:        "",
            cartCounter:        0,
            total:              0,
            localStorage:       true
        }
    },
    created(){
        this.loadData()
    },
    mounted() {
    },
    methods: {
        loadData() {
            if (localStorage.getItem("cart") != null) {
                this.cart = JSON.parse(localStorage.getItem("cart"));
                this.total = JSON.parse(localStorage.getItem("total"));
                this.localStorage = true
            }
            axios("/api/products")
                .then((response) => {
                    console.log(response);
                    this.products = response.data
                    this.filteredProducts = this.products
                    this.categories = [... new Set(this.products.map(product => product.category))]
                    this.cart.forEach((order) => {
                        this.filteredProducts.map((prod) => {
                            if (prod.id == order.id) {
                                order.price = prod.price;
                                if (prod.stock - order.quantity >= 0) {
                                    order.stock = prod.stock - order.quantity;
                                    prod.stock = order.stock
                                    prod.quantity = order.quantity
                                } else {
                                    this.stockInconsistency = true;
                                }
                            }
                            this.getTotal()
                            localStorage.setItem('cart', JSON.stringify(this.cart))
                            localStorage.setItem('total', JSON.stringify(this.total))
                        });
                    });
                    if (this.stockInconsistency) {
                        console.log("Stock inconsistency.");
                        localStorage.clear();
                        Swal.fire({
                            showConfirmButton: false,
                            timer: 2000,
                            timerProgressBar: true,
                            icon: "error",
                            title: `Stock inconsistency`,
                        });
                        setTimeout(() => location.replace("/web-golf/mockup.html"), 2000);
                    }
                })
                .catch((error) => console.log(error));
        },
        capitalize(string) {
            return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase()
        },
        searchByText() {
            let filteredProducts = this.products.filter((product) =>
                product.name.toLowerCase().includes(this.searchValue.toLowerCase())
            );
            return filteredProducts;
        },
        filterByCategory(products) {
            let filteredProducts = products.filter((product) => product.category == this.categoryToFilter);
            return filteredProducts;
        },
        crossFilter() {
            const filterProductsBySearch = this.searchByText();
            const filterProductsByCategory = this.filterByCategory(filterProductsBySearch);
            if (this.categoryToFilter == "") {
                if (filterProductsBySearch.length !== 0) {
                    this.filteredProducts = filterProductsBySearch;
                } else {
                    this.filteredProducts = [];
                }
            } else if (filterProductsByCategory.length === 0) {
                this.filteredProducts = [];
            } else {
                this.filteredProducts = filterProductsByCategory;
            }
        },
        clearFilter() {
            this.categoryToFilter = "";
            this.searchValue = "";
            this.crossFilter();
        },
        checkProductInCart(product) {
            return this.cart.find(prod => product.id === prod.id)
        },
        addProduct(product) {
            let index= this.cart.findIndex(prod => prod.id === product.id);
            if (index < 0) {
                console.log('Pushed')
                product.quantity = 1;
                product.stock--
                this.cart.push(product);
            } else {
                console.log("Added")
                this.cart[index].quantity++
                this.cart[index].stock--
            }
            this.getTotal()
            localStorage.setItem('cart', JSON.stringify(this.cart))
            localStorage.setItem('total', JSON.stringify(this.total))
            this.filteredProducts = this.updateCartState();
        },
        updateCartState() {
            return this.products.map(product => {
                let index = this.cart.findIndex(prod => prod.id === product.id);
                if (index>=0) {
                    product.quantity = this.cart[index].quantity;
                    product.stock = this.cart[index].stock
                }
                return product;
            })
        },
        showDetail(product) {
            this.productDetail = product
            console.log(this.productDetail)
        },
        removeProductQuantity(product) {
            let index = this.cart.findIndex(prod => prod.id === product.id)
            this.cart[index].quantity--
            this.cart[index].stock++
            this.filteredProducts = this.updateCartState();
            if (this.cart[index].quantity <= 0) {
                this.cart.splice(index, 1);
            }
            this.getTotal()
            localStorage.setItem('cart', JSON.stringify(this.cart))
            localStorage.setItem('total', JSON.stringify(this.total))
            this.filteredProducts = this.updateCartState();
        },
        removeProduct(product) {
            axios('/api/products')
                .then(response => {
                    let index = this.cart.findIndex(prod => prod.id === product.id)
                    this.cart[index].stock += this.cart[index].quantity
                    this.cart[index].quantity = 0
                    this.filteredProducts = this.updateCartState();
                    this.cart.splice(index, 1);
                    this.getTotal()
                    localStorage.setItem('cart', JSON.stringify(this.cart))
                    localStorage.setItem('total', JSON.stringify(this.total))
                    // this.filteredProducts = this.updateCartState();
                })
                .catch(error => console.log(error))
        },
        emptyCart() {
            localStorage.clear()
            this.loadData()
            this.cart = []
            this.total = 0
        },
        getTotal(product) {
            this.total = this.cart.reduce((acc, product) => acc + Number(product.price * product.quantity), 0)
        }
    },
    computed: {
        countProducts() {
            this.cartCounter = this.cart.reduce((acc, product) => acc + product.quantity, 0);
        }
    }
}).mount('#app')