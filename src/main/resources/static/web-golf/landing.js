const { createApp } = Vue

window.onscroll = function(){
    if(document.documentElement.scrollTop > 100){
    document.querySelector(".go-top-container")
    .classList.add("show-own")
    }
    else{
    document.querySelector(".go-top-container")
    .classList.remove("show-own")
    }
}
document.querySelector(".go-top-container")
.addEventListener("click",() =>{
    window.scrollTo({
    top:0,
    behavior:"smooth"
    });
});

createApp({
    data() {
        return {
            products: [],
        }
    },
    created(){
        this.loadData()
    },

    mounted() {
        
    },
    methods: {
        loadData() {
            axios('/api/products')
                .then(response => {
                    console.log(response)
                    this.products = response.data
                    console.log(this.products)
                })
        }
    },
    computed: {
        
    },

}).mount('#app')