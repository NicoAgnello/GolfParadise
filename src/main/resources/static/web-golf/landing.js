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
            lastProducts:[],
            
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
                    
                    this.products = response.data
                    this.lastProducts = this.products.sort((a,b)=> b.id - a.id).slice(0,8)
                    /* console.log(this.lastProducts) */
                    


                })
        }
    },
    computed: {
        
    },

}).mount('#app')