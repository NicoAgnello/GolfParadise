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


const { createApp } = Vue

createApp({

    data() {
        return {
        
        }
    },
    created(){
    
    },

    mounted() {
        
    },
    methods: {
        
    },
    computed: {
        
    },

}).mount('#app')