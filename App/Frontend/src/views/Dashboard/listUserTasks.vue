<template>
  <div class="container">
    <div
      class="project"
      v-for="(task, index) in tasks"
      :key="index"
    >
      <div class="row d-flex align-items-center">
        <div class="col-sm-4">
          <div class="current-status">
            {{ task.status }}
          </div>
        </div>
        <div class="col-sm-6">
          {{ task.name }}
        </div>
      </div>
    </div>
  </div>
</template>
 
<script>
  export default {
    data: () => ({
      tasks: [
        {status: 'Aguardando retorno', name:'SPRINT6'},
        {status: 'Aguardando retorno', name:'SPRINTFINAL'},
        {status: 'Concluído', name:'SPRINT'}
      ]
    }),
 
    // mounted() {
    //   this.getTasks();
    // },
 
    // methods: {
    //   getTasks() {
    //     axios.get('rota', {
    //       id: localStorage.getItem('id')
    //     })
    //       .then(({data}) => {
    //         this.tasks = data;            
    //       }).catch((e) => {
    //         console.log(e);
    //       })
    //   }
    // }
  };
</script>