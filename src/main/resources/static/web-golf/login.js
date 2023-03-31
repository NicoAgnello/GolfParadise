const { createApp } = Vue
const useVuelidate  = Vuelidate.useVuelidate
const required      = VuelidateValidators.required
const email         = VuelidateValidators.email
const minLength     = VuelidateValidators.minLength

createApp({
    data() {
        return {
            container:            document.querySelector(".container"),
            v$:                   useVuelidate(),
            email:                "",
            password:             "",
            newClientFirstName:   "",
            newClientLastName:    "",
            newClientEmail:       "",
            newClientPassword:    "",
            invalidCredentials:   false,
            emailInUse:           false
            // v$:                 useVuelidate(),
            // products:           [],
            // cart:               [],
            // total:              0,
            // deliveryCost:       0,
            // cardNumber:         "",
            // cardCvv:            "",
            // deliveryAddress:    "",
            // zipCode:            "",
            // stockInconsistency: false
        }
    },
    validations() {
        return {
            email:              { required, email },
            password:           { required },
            newClientFirstName: { required },
            newClientLastName:  { required },
            newClientEmail:     { required, email },
            newClientPassword:  { required, minLength: minLength(8) }
        }
    },
    created() {
        // this.loadData()
    },
    methods: {
        signUpToggle() {
            this.container.classList.add("sign-up-mode");
        },
        signInToggle() {
            this.container.classList.remove("sign-up-mode");
        },
        logUser() {
            axios.post('/api/login',
                `email=${this.email}&password=${this.password}`,
                {
                    headers: {
                        'content-type':'application/x-www-form-urlencoded'
                    }
                })
                .then(response => {
                    console.log(response)
                    if (this.email === "admin@admin.com") {
                        location.replace("/admin-panel/index-admin.html")
                    } else {
                        if (document.referrer.includes('products')) {
                            location.replace("/web-golf/checkout.html")
                        } else {
                            history.back()
                        }
                    }
                })
                .catch(error => {
                    console.log(error.message)
                    this.invalidCredentials = true
                })
        },
        registerUser() {
            axios.post('/api/clients',
                `firstName=${this.newClientFirstName}&lastName=${this.newClientLastName}&email=${this.newClientEmail}&password=${this.newClientPassword}`,
                {
                    headers:
                        {
                            'content-type':'application/x-www-form-urlencoded'
                        }
                })
                .then(() => {
                    this.email    = this.newClientEmail
                    this.password = this.newClientPassword
                    this.logUser()
                })
                .catch(error => {
                    console.log(error.response.data)
                    if (error.response.data === "Email already in use") {
                        this.emailInUse = true
                    }
                })
        },
        submitLoginForm(e) {
            e.preventDefault()
            this.v$.email.$touch();
            this.v$.password.$touch();
            if (!this.v$.email.$invalid && !this.v$.password.$invalid) {
                this.logUser()
            }
        },
        submitSignUpForm(e) {
            e.preventDefault()
            this.v$.newClientFirstName.$touch();
            this.v$.newClientLastName.$touch();
            this.v$.newClientEmail.$touch();
            this.v$.newClientPassword.$touch();
            if (!this.v$.newClientFirstName.$invalid &&
                !this.v$.newClientLastName.$invalid &&
                !this.v$.newClientEmail.$invalid &&
                !this.v$.newClientPassword.$invalid) {
                this.registerUser()
            }
        },
    }
}).mount('#app')