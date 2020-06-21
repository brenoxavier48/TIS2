<template>
  <modal-base 
    :isVisible="createProjectModal"
    @closeModal="(e) => {createProjectModal = e}"
  >
    <div class="input-group">
      <label>Nome do projeto</label>
      <input 
        type="text"
        v-model="formData.nome"
      />
    </div>

    <!-- <div class="input-group">
      <label>Responsável</label>
      <input 
        type="text"
      />
    </div> -->

    <!-- <div class="input-group">
      <label>Status</label>
      <select>
        <option value="aguardando retorno">Aguardando retorno</option>
        <option value="concluido">Concluido</option>
        <option value="atrasado">Atrasado</option>
        <option value="andamento">Em andamento</option>
      </select>
    </div> -->

    <div class="input-group">
      <label>Descrição</label>
      <textarea v-model="formData.descricao"/>
    </div>

    <button @click="createProject">Criar projeto</button>
  </modal-base>
</template>

<script>
  import modalBase from '@/components/modal-base.vue';
  import axios from 'axios';
  
  export default {
    components: {
      modalBase
    },

    props: {
      isVisible: {
        type: Boolean,
        default: false
      }
    },

    data: () => ({
      createProjectModal: undefined,
      formData: {
        nome: '',
        descricao: ''
      }
    }),

    mounted() {
      this.createProjectModal = this.isVisible;
    },

    watch: {
      isVisible() {
        this.createProjectModal = this.isVisible;
      },

      createProjectModal() {
        this.$emit('closeModal', this.createProjectModal);
      }
    },

    //requisição para criar projeto
    methods: {
      createProject() {
        // console.log(JSON.stringify(this.formData))
        // axios.post(`http://localhost:3030/project?userid=${localStorage.getItem('id')}`, JSON.stringify(this.formData), {})
        fetch(`http://localhost:3030/project?userid=${localStorage.getItem('id')}`, {
        method:'POST',
        body:JSON.stringify(this.formData)
        })
          .then((resp) => resp.json())
          .then(data => {
            console.log(data);
            this.$emit('closeModal', false);
          }).catch((e) => {
            console.log(e);
          })
      }
    }
  };
</script>

