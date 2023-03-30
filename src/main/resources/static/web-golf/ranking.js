const { createApp } = Vue

createApp({
    data() {
        return {
            data: [],
            players: [],
            year: "",
            ranking: "",
            statusAPI: null,
            loader: false


        }
    },

    methods: {
        loadData() {
            this.players = []
            this.statusAPI = null
            this.loader = true
            const options = {
                method: 'GET',
                url: 'https://live-golf-data.p.rapidapi.com/stats',
                params: { year: this.year, statId: '186' },
                headers: {
                    'X-RapidAPI-Key': '08f889752dmsh66e4e5b5224dd6ep1d6aa0jsn6647191dfdc2',
                    'X-RapidAPI-Host': 'live-golf-data.p.rapidapi.com'
                }
            };
            axios.request(options)
                .then(data => {
                    this.players = data.data.rankings
                    console.log(this.players)
                    this.loader = false
                })
                .catch(err => {
                    console.log(err)
                    this.statusAPI = err.response.status
                    this.loader = false
})

        }
    }
}).mount('#App')