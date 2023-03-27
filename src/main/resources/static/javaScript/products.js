const { createApp } = Vue

createApp({

    data() {
        return {
        form:{
            type:0, //0 = login, 1 = Registro, 2 = Recuperar contraseña
            email:"", 
            password:"", 
            password2:""
            },
        }
    },
    created(){
    
    
    },

    mounted() {
        
    },
    computed: {
        validEmail(){
            var exp = /^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/i;
            if(exp.test(this.form.email)){
                return false;
            }else{
                return true;
            }
        },
        validPassword(){
            var exp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,15}/;
            if(exp.test(this.form.password)){
                return false;
            }else{
                return true;
            }
        },
        title(){
            return (this.form.type == 0) ? 'Login':(this.form.type==1)?'Register':'Recover Password'
        },
        validRepetirPassword(){
            if(this.form.password == this.form.password2){
                return false;
            }else{
                return true;
            }
        }
    },
    watch: {
        
    },
    methods: {
        sendForm(){
            if(this.validType()){
                console.log(this.form);
            }
        },
        validType(){
            if(this.form.type == 0 && !this.validEmail && !this.validPassword){
                return true;
            }
            else if(this.form.type == 1 && !this.validEmail && !this.validRepetirPassword){
                return true;
            }
            else if(this.form.type == 2 && !this.validEmail){
                return true;
            }
            return false;
            
        },
        
    }

}).mount('#app')