<template>
  <div class="search">
    <div class="container">
      <div class="row">
        <div class="col-sm-12">
          <input 
            v-model="searchString"
            type="text"
            @input="filterProjects"
          />
          <button @click="$router.push({ name: 'userTasks' })">Minhas Tarefas</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data: () => ({
      searchString: '',
    }),

    methods: {
      filterProjects() {
        this.$emit('searchProject', this.searchString);
      },
    }
  };
</script>