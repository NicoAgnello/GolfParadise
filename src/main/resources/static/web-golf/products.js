const { createApp } = Vue

createApp({

    data() {
        return {
            products:           [],
            categories:         [],
            filteredProducts:    [],
            cart:               [],
            categoryToFilter:   "",
            searchValue:        "",
            total:               0
        }
    },
    created(){
        this.loadData()
    },
    mounted() {
    },
    methods: {
        loadData() {
            if (localStorage.getItem('cart') != null) {
                this.cart       = JSON.parse(localStorage.getItem('cart'))
                this.total      = JSON.parse(localStorage.getItem('total'))
            }
            axios('http://localhost:8080/api/products')
                .then(response => {
                    console.log(response)
                    this.products = response.data
                    this.filteredProducts = this.products
                    this.categories = [... new Set(this.products.map(product => product.category))]
                })
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
            console.log(this.categoryToFilter)
            const filterProductsBySearch = this.searchByText();
            const filterProductsByCategory = this.filterByCategory(filterProductsBySearch);
            if (this.categoryToFilter == "") {
                if (filterProductsBySearch.length !== 0) {
                    this.filteredProducts = filterProductsBySearch;
                } else {
                    this.filteredProducts = null;
                }
            } else if (filterProductsByCategory.length === 0) {
                this.filteredProducts = null;
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
            if (this.checkProductInCart(product)) {
                product.quantity++
            } else {
                this.cart.push(product)
                product.quantity = 1
            }
            product.stock--
            this.getTotal()
            localStorage.setItem('cart', JSON.stringify(this.cart))
            localStorage.setItem('total', JSON.stringify(this.total))
        },
        removeProductQuantity(product) {
            product.quantity--
            product.stock++
            if (product.quantity === 0) {
                this.cart.splice(this.cart.indexOf(product), 1)
            }
            this.getTotal()
            localStorage.setItem('cart', JSON.stringify(this.cart))
            localStorage.setItem('total', JSON.stringify(this.total))
        },
        removeProduct(product) {
            axios('/api/products')
                .then(response => {
                    console.log(response)
                    product.stock = response.data.find(prod => prod.id === product.id).stock
                    product.quantity = 0;
                    this.cart.splice(this.cart.indexOf(product), 1)
                    this.getTotal()
                    localStorage.setItem('cart', JSON.stringify(this.cart))
                    localStorage.setItem('total', JSON.stringify(this.total))
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
    }
}).mount('#app')