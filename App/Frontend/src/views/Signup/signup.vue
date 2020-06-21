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
              <label for="">Username:</label>
              
              <input 
                v-model="formData.userName"
                type="text"
              >
            </div>

            <div class="input-group">
              <label for="">Nome:</label>
              
              <input 
                v-model="formData.nome"
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
                  Já possui conta? 
                  <a href="/login">Fazer login</a>
                </p>
              </div>
            </div>

            <button @click="signup()">Criar Conta</button>
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
        nome: '',
        senha: '',
        userName: ''
      }
    }),

    methods: {
      //Requisição pra fazer login
      signup() {
        console.log(JSON.stringify(this.formData))
        axios.post('http://localhost:3030/user', JSON.stringify(this.formData))
          .then(({ data }) => {
            console.log(data)
            if (data.success) {
              localStorage.setItem('id', data.id);
              this.$router.push({name: 'login'});
            } else {
              alert('Ocorreu um erro ao criar usuario!');
            }
          }).catch((e) => {
            console.log(e);
          })
      }
    }
  };
</script>