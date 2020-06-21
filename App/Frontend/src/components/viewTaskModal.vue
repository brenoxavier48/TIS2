<template>
  <modal-base 
    :isVisible="viewTaskModal"
    @closeModal="(e) => {viewTaskModal = e}"
  >
    <div class="input-group">
      <label>Nome do projeto</label>
      <input 
        type="text"
        v-model="formData.nome"
        readonly
      />
    </div>

    <div class="input-group">
      <label>Responsável</label>
      <input 
        type="text"
        v-model="formData.userid"
        readonly
      />
    </div>

    <div class="input-group">
      <label>Status</label>
      <select 
        v-model="formData.status"
        disabled
      >
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
        readonly
      />
    </div>

    <div class="input-group">
      <label>Comentários</label>
      <textarea 
        v-model="formData.comments"
      />
    </div>

    <button>Adicionar comentários</button>
  </modal-base>
</template>

<script>
  import modalBase from '@/components/modal-base.vue';
  
  export default {
    components: {
      modalBase
    },

    props: {
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
      viewTaskModal: undefined,

      formData: {
        nome: '',
        status: '',
        response: '',
        description: '',
        comments: ''
      }
    }),

    mounted() {
      this.viewTaskModal = this.isVisible;
      this.formData = {...this.task};
    },

    watch: {
      isVisible() {
        this.viewTaskModal = this.isVisible;
        this.formData = {...this.task};
      },

      viewTaskModal() {
        this.$emit('closeModal', this.viewTaskModal);
      }
    }
  };
</script>