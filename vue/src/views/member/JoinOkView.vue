<template>
  <v-container fluid>
    <v-row class="mb-8">
      <v-col cols="12"><h2>π₯³νμκ°μ μλ£ π</h2></v-col>
    </v-row>
    <v-layout class="mb-16">
      <v-col cols="12" class="mb-16">
        <h3>
          μλ ₯νμ  {{ email }} λ‘ <br />
          μΈμ¦ λ©μΌμ μ μ‘νμ΅λλ€.<br /><br />
          λ©μΌμ μΈμ¦νκ³  <br />
          Karrot λ§μΌμ μμν΄λ³΄μΈμ.
        </h3>
      </v-col>
    </v-layout>
    <v-row>
      <v-col cols="12" class="mt-16" style="color: red">κ΅¬ν μμ </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" class="mt-16">
        <v-btn href="/login" class="text-white" color="#ff931e" block>
          λ‘κ·ΈμΈ νλ¬κ°κΈ°
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  export default {
    data() {
      return {
        email: "μ΄λ©μΌ",
      };
    },
  };
</script>

<style scoped>
  .v-btn {
    font-family: "EliceDigitalBaeum-Bd", serif;
    font-size: 18px;
    width: 300px;
    height: 40px;
  }
</style>
