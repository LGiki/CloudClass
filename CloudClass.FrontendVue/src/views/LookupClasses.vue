<template >
  <body style="height: 100%;width: 100%">
  <div>
    <v-toolbar
        dark
        flat
        src="../assets/vbanner.jpg"
    >
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>

      <v-toolbar-title >班课管理</v-toolbar-title>

      <v-spacer></v-spacer>
      <!--
      <v-btn icon>
        <v-icon>mdi-export</v-icon>
      </v-btn>
      -->
    </v-toolbar>

    <v-card
        class="mx-auto"
        tile
    >
      <v-list v-for="cla in classes" :key="cla.c">
      <v-list-item>
        <v-list-item-content>{{cla.c}}</v-list-item-content>
      </v-list-item>

        <v-list-item style="margin-top: -20px">
          <v-icon small color="red darken-2"  >mdi-account-circle</v-icon> <button style="font-size: small;margin-right: 10px">成 员</button>

          <v-icon small color="orange darken-2"  >mdi-clipboard-text-outline</v-icon> <button style="font-size: small;margin-right: 10px">统 计</button>
          <v-icon small color="green darken-2" >mdi-check-circle</v-icon> <button style="font-size: small;margin-right: 10px">签 到</button>
        </v-list-item>


      </v-list>
    </v-card>


  </div>
  </body>
</template>


<script>
export default {
name: "lookup_classes",
  data() {
    return {
      classes: [
        {c:"工程训练1班"},
        {c:"工程训练2班"},
        {c:"工程训练3班"}
        ]
    }
  }
}
</script>

<style scoped>

</style>