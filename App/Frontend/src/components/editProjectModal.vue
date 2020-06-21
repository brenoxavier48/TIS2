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
        v-model="formData.response"
      />
    </div>

    <div class="input-group">
      <label>Status</label>
      <select v-model="formData.status">
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

    <button @click="editProjectData()">Editar dados do projeto</button>
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
      },

      project: {
        type: Object,
        required: true
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
      this.formData = {...this.project};
    },

    watch: {
      isVisible() {
        this.createProjectModal = this.isVisible;
        this.formData = {...this.project};
      },

      createProjectModal() {
        this.$emit('closeModal', this.createProjectModal);
      }
    },

    //requisição para editar o projeto
    methods: {
      editProjectData() {
        console.log(this.project,'ed', this.formData)
        axios.post(`http://localhost:3030/project/${this.project.id}`, JSON.stringify(this.formData))
          .then(({data}) => {
            console.log(data);
            this.$emit('closeModal', false);
          }).catch((e) => {
            console.log(e);
          })
      }
    }
  };
</script>