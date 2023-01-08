// Styles
import "@mdi/font/css/materialdesignicons.css";
import "vuetify/styles";
import "material-design-icons-iconfont/dist/material-design-icons.css";
// Vuetify
import { createVuetify } from "vuetify";
import { aliases, mdi } from "vuetify/lib/iconsets/mdi";

export default createVuetify({
  icons: {
    defaultSet: "mdi",
    aliases,
    sets: {
      mdi,
    },
  },
});
