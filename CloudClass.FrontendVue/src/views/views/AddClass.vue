<template>
  <v-container>
    <v-menu offset-y>
      <template v-slot:activator="{ on, attrs }">
        <v-text-field
          prepend-icon="mdi-card-text"
          v-model="courseName"
          :rules="[rules.required]"
          v-bind="attrs"
          v-on="on"
          width="300"
          label="班课名称"
          >{{ school }}
        </v-text-field>
      </template>
      <v-list>
        <v-list-item
          v-for="(item, index1) in classes"
          :key="index1"
          @click="addClaContent(index1)"
        >
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>

    <v-text-field
      prepend-icon="mdi-account-circle"
      v-model="className"
      :rules="[rules.required]"
      name="input-10-1"
      label="班级名称"
    ></v-text-field>
    <v-text-field
      prepend-icon="mdi-format-color-highlight"
      v-model="classHour"
      :rules="[rules.required]"
      label="课时"
    >
    </v-text-field>
    <v-text-field
      prepend-icon="mdi-numeric-1-circle-outline"
      v-model="semester"
      :rules="[rules.required]"
      label="学期"
    >
    </v-text-field>

    <v-menu offset-y>
      <template v-slot:activator="{ on, attrs }">
        <v-text-field
          prepend-icon="mdi-school"
          v-model="school"
          :rules="[rules.required]"
          v-bind="attrs"
          v-on="on"
          width="300"
          label="请选择学校"
          >{{ school }}
        </v-text-field>
      </template>
      <v-list>
        <v-list-item
          v-for="(item, index1) in schools"
          :key="index1"
          @click="addSchContent(index1)"
        >
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>

    <v-menu offset-y v-if="school !== ''">
      <template v-slot:activator="{ on, attrs }">
        <v-text-field
          prepend-icon="mdi-school-outline"
          v-model="department"
          :rules="[rules.required]"
          v-bind="attrs"
          v-on="on"
          width="300"
          label="请选择院系"
        >
          {{ department }}
        </v-text-field>
      </template>
      <v-list>
        <v-list-item
          v-for="(item, index) in schools[schoolIndex].depart"
          :key="index"
          @click="addDepContent(index)"
        >
          <v-list-item-title v-if="school !== ''">{{
            item.sname
          }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>

    <div class="text-center">
      <v-btn dark @click="judgeAddClass" color="primary" class="mt-4" block>
        添加
      </v-btn>
      <v-snackbar v-model="snackbar" timeout="3000">
        {{ text }}

        <template v-slot:action="{ attrs }">
          <v-btn color="pink" text v-bind="attrs" @click="snackbar = false">
            关闭
          </v-btn>
        </template>
      </v-snackbar>
    </div>
  </v-container>
</template>

<script>
import { addClass, queryAllCollege } from "../api/class";

export default {
  data() {
    return {
      name: "AddClass",
      className: "",
      courseName: "",
      classCode: "10001",
      classHour: 24,
      semester: "2021-2022-1",
      schoolIndex: 0,
      rules: {
        required: (value) => !!value || "必填",
      },
      schools: [
        {
          title: "福州大学",
          depart: [
            {
              s_id: 1,
              title: "福州大学",
              depart: [
                { sname: "数学与计算机科学学院", sid: 2 },
                { sname: "经管学院", sid: 3 },
              ],
            },
          ],
        },
      ],
      classes: [
        {
          title: "工程实践",
        },
      ],
      school: "",
      schoolId: 0,
      department: "",
      departmentId: 0,
      snackbar: false,
      text: `添加班课成功`,
    };
  },
  methods: {
    async judgeAddClass() {
      //判断是否都已填写
      if (
        this.className == "" ||
        this.courseName == "" ||
        this.classHour < 1 ||
        this.semester == "" ||
        this.school == "" ||
        this.department == ""
      ) {
        this.snackbar = true;
        this.text = "请完整填写班课信息";
      } else {
        //教师id，班级名称，班课名称，学期，学时，学校，学院
        let result = await addClass(
          //      localStorage.getItem("teacherId"),
          //暂时不用peid
          //  "26",
          this.className,
          this.courseName,
          this.semester,
          this.classHour + "",
          this.schoolId + "",
          this.departmentId + ""
        );
        switch (result.data.code) {
          case "200":
            this.text = "创建成功";
            this.snackbar = true;
            //提取班级号
            this.classCode = result.data.cNumber;

            this.$router.push({
              path: "/classDetail",
              query: { showSuccess: true, id: this.classCode },
            });
            break;

          default:
            this.text = result.data.msg;
            this.snackbar = true;
        }
      }

      //逻辑判断班级号是否重复,返回是否添加成功
    },
    addDepContent(index) {
      if (this.school === "") {
        alert("请先选择学校");
        return;
      }
      this.department = this.schools[this.schoolIndex].depart[index].sname;
      this.departmentId = this.schools[this.schoolIndex].depart[index].sid;
    },
    addSchContent(index1) {
      this.school = this.schools[index1].title;
      this.schoolId = this.schools[index1].s_id;
      this.department = "";
      this.departmentId = 0;
    },
    addClaContent(index1) {
      this.courseName = this.classes[index1].title;
    },
  },
  async mounted() {
    let result = await queryAllCollege();
    if (result.data.code === null) {
      this.text = "网络连接失败";
      this.snackbar = true;
      return;
    }
    switch (parseInt(result.data.code)) {
      case 200:
        console.log("查询成功");
        this.schools = result.data.data;
        break;
      default:
        this.text = result.data.msg;
        this.snackbar = true;
    }
  },
};
</script>

<style scoped></style>
