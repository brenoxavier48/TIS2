<template>
  <section id="login">
    <div class="container h-100">
      <div class="row h-100">
        <div class="col-sm-12 h-100 d-flex justify-content-center align-items-center">
          <form @submit.prevent>
            <img 
              src="@/assets/images/logo.jpeg" 
              alt="Dev Worth"
            >

            <div class="input-group">
              <label for="">E-mail:</label>
              
              <input 
                v-model="formData.userName"
                type="text"
              >
            </div>

            <div class="input-group">
              <label for="">Senha:</label>
              
              <input 
                v-model="formData.senha"
                type="password"
              >
            </div>

            <div class="row">
              <div class="col-sm-12 d-flex justify-content-end">
               <p>
                  Não possui conta? 
                  <a href="/signup">Cadastre-se</a>
                </p>
              </div>
            </div>

            <button @click="login()">Login</button>
          </form>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
  import axios from 'axios';

  export default {
    data: () => ({
      formData: {
        userName: '',
        senha: ''
      }
    }),

    methods: {
      //Requisição pra fazer login
      login() {
        axios.post('http://localhost:3030/user/login', JSON.stringify(this.formData))
          .then(({data}) => {
            console.log(data)
            if (data.id) {
              alert('OKAY');
              localStorage.setItem('id', data.id);
              this.$router.push({name: 'dashboard'});
             
            } else {
              alert('Usuario ou senha incorretos!');
            }
          }).catch((e) => {
            console.log(e);
          })
      }
    }
  };
</script>