<template>
  <div 
    v-if="isVisible"
  >
    <div class="openedModal">
      <slot />
    </div>

    <div 
      class="fundo" 
      @click="closeModal()"
    />
  </div>
</template>

<script>
  export default {
    props: {
      isVisible: {
        type: Boolean,
        default: false
      }
    },
    
    methods: {
      closeModal() {
        this.$emit('closeModal', false);
      }
    }
  };
</script>