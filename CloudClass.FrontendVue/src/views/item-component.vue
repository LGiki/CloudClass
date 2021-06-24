<template>
  <v-list class="pa-1">
    <v-list-item :key="source.uid">
      <v-list-item-content style="font-size: 14px">
        <v-list-item-title
          v-html="source.name"
          style="font-size: 14px"
        ></v-list-item-title>
      </v-list-item-content>
      <v-list-item-content class="ml-16" style="font-size: 13px; color: gray">
        {{ source.stuNumber }}</v-list-item-content
      >
    </v-list-item>
    <v-divider :key="source.uid"></v-divider>
  </v-list>
</template>
<script>
export default {
  name: "item-component",
  props: {
    index: {
      // index of current item
      type: Number,
    },
    source: {
      // here is: {uid: 'unique_1', text: 'abc'}
      type: Object,
      default() {
        return {};
      },
    },
  },
};
</script>

<style scoped></style>
