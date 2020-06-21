<template>
  <modal-base 
    :isVisible="createTaskModal"
    @closeModal="(e) => {createTaskModal = e}"
  >
    <div class="input-group">
      <label>Nome do projeto</label>
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
      <textarea v-model="formData.descricao"/>
    </div>

    <button @click="editTaskData()">Editar dados da tarefa</button>
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
      },

      task: {
        type: Object,
        required: true
      }
    },

    data: () => ({
      createTaskModal: undefined,

      formData: {
        name: '',
        status: '',
        response: '',
        description: '',
      }
    }),

    mounted() {
      this.createTaskModal = this.isVisible;
      this.formData = {...this.task};
    },

    watch: {
      isVisible() {
        this.createTaskModal = this.isVisible;
        this.formData = {...this.task};
      },

      createTaskModal() {
        this.$emit('closeModal', this.createTaskModal);
      }
    },

    //requisição para editar a tarefa
    methods: {
      editTaskData() {
        fetch(`http://localhost:3030/task/${this.task.id}${ !isNaN(this.formData.userid) ? `userid=${this.formData.userid}` : ''}`,
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