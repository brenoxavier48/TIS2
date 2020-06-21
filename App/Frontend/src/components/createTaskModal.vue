<template>
  <modal-base 
    :isVisible="createTaskModal"
    @closeModal="(e) => {createTaskModal = e}"
  >
    <div class="input-group">
      <label>Nome da tarefa</label>
      <input 
        type="text"
        v-model="formData.nome"
      />
    </div>

    <div class="input-group">
      <label>Responsável</label>
      <input 
        type="text"
        v-model="formData.userid"
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
    </div>

    <div class="input-group">
      <label>Descrição</label>
      <textarea
      v-model="formData.descricao"
       />
    </div>

    <button @click="createTask">Criar tarefa</button>
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
      project: {
        type: Object,
        required: true
      }, 

      isVisible: {
        type: Boolean,
        default: false
      }
    },

    data: () => ({
      createTaskModal: undefined,
      formData: {
        nome: '',
        descricao: '',
        status: '',
        userid: ''
      }
    }),

    mounted() {
      this.createTaskModal = this.isVisible;
    },

    watch: {
      isVisible() {
        this.createTaskModal = this.isVisible;
      },

      createTaskModal() {
        this.$emit('closeModal', this.createTaskModal);
      }
    },

    //requisição para criar tarefa
    methods: {
      createTask() {
        console.log(this.formData.userid)
        fetch(`http://localhost:3030/task?${ !isNaN(this.formData.userid) && this.formData.userid > 0 ? `userid=${this.formData.userid}` : ''}&projectid=${this.project.id}`,
        {
          method:'POST',
          body:JSON.stringify(this.formData)
        } )
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