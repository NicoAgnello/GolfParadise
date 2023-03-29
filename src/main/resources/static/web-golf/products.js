const { createApp } = Vue

createApp({

    data() {
        return {
            products: [],
            clubsProducts: [],
            bagsProducts:[],
            ballsProducts: [],
            clothesProducts:[],
            accesoriesProducts: [],
            shoesProducts: []
        }
    },
    created(){
        this.loadData()
    },

    mounted() {
        
    },
    methods: {
        loadData() {
            axios('http://localhost:8080/api/products')
                .then(response => {
                    console.log(response)
                    this.products = response.data
                    this.clubsProducts = this.products.filter(product => product.category == "CLUBS")
                    this.bagsProducts = this.products.filter(product => product.category == "BAGS")
                    this.ballsProducts = this.products.filter(product => product.category == "BALLS")
                    this.clothesProducts = this.products.filter(product => product.category == "CLOTHES")
                    this.accesoriesProducts = this.products.filter(product => product.category == "ACCESORIES")
                    this.shoesProducts = this.products.filter(product => product.category == "SHOES")
                    /* console.log(this.accesoriesProducts); */
                })
        }
    },
    computed: {
        orderForPrice(){
            this.clubsProducts = this.products.filter(product => product.category == "CLUBS").sort((a,b)=> a.price - b.price)
        }
    },
    watch: {
        
    }
    

}).mount('#app')