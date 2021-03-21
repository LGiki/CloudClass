<template>
  <v-container>
    <v-carousel hide-delimiter-background show-arrows-on-hover v-model="model">
      <v-carousel-item v-for="(content, index) in contents" :key="index">
        <v-sheet :color="content.color" height="100%" tile>
          <v-row class="fill-height" align="center" justify="center">
            <div class="display-3">{{ content.content }}</div>
          </v-row>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    model: 0,
    contents: [
      {
        color: "primary",
        content: "到云 V1.0",
      },
      {
        color: "secondary",
        content: "到云 V1.0",
      },
      {
        color: "yellow darken-2",
        content: "到云 V1.0",
      },
    ],
  }),
};
</script>
