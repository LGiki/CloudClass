<template>
  <v-list class="pa-1">
    <v-list-item :key="source.pe_id">
      <v-list-item-content style="font-size: 14px">
        <v-list-item-title
          v-html="source.peName"
          style="font-size: 14px"
        ></v-list-item-title>
      </v-list-item-content>
      <v-list-item-content class="ml-16" style="font-size: 13px; color: gray">
        {{ source.username }}</v-list-item-content
      >
    </v-list-item>
    <v-divider :key="source.pe_id"></v-divider>
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
