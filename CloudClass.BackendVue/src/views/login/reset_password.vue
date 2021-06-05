<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :model="loginForm"
      :rules="loginRules"
      class="login-form"
      auto-complete="on"
      label-position="left"
    >

      <div class="title-container">
        <h3 class="title">重置密码</h3>
      </div>

      <div style="width: 100%;display: inline-grid;grid-template-columns: 3fr 1fr;grid-column-gap: 20px;">
        <el-form-item prop="phone">
          <div>
            <span class="svg-container">
              <svg-icon icon-class="phone" />
            </span>
            <el-input
              key="phone"
              ref="phone"
              v-model="loginForm.phone"
              type="text"
              placeholder="手机号"
              name="phone"
              tabindex="2"
              auto-complete="on"
              @keyup.enter.native="handleLogin"
            />
          </div>
        </el-form-item>
        <el-button
          :disabled="sendValidationButton.disabled"
          type="primary"
          style="width:100%;margin-bottom:22px;"
          @click="handleSendValidationCode"
        >
          {{ sendValidationButton.title }}
        </el-button>
      </div>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          key="validationCode"
          ref="validationCode"
          v-model="loginForm.validationCode"
          type="validationCode"
          placeholder="验证码"
          name="validationCode"
          tabindex="2"
          auto-complete="on"
          @keyup.enter.native="handleLogin"
        />
      </el-form-item>
      <el-button
        :loading="loading"
        type="primary"
        style="width:100%;margin-bottom:30px;"
        @click.native.prevent="handleLogin"
      >下一步
      </el-button>

    </el-form>
  </div>
</template>

<script>
import { validatePhone } from '@/utils/validate'

export default {
  name: 'ResetPassword',
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value.length === 0) {
        callback(new Error('密码不能为空'))
      } else {
        callback()
      }
    }
    return {
      active: 0,
      loginForm: {
        username: '',
        password: '',
        phone: '',
        validationCode: ''
      },
      loginRules: {
        phone: [{ required: true, trigger: 'blur', validator: validatePhone }],
        password: [{ required: true, trigger: 'blur', validator: validatePassword }]
      },
      loading: false,
      redirect: undefined,
      sendValidationButton: {
        title: '发送验证码',
        disabled: false
      }
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    handleSendValidationCode() {
      this.sendValidationButton.disabled = true
      const TIME_COUNT = 60
      this.sendValidationButton.title = `${TIME_COUNT}s 后重试`
      if (!this.timer) {
        let count = TIME_COUNT
        console.log(count)
        this.timer = setInterval(() => {
          if (count > 0 && count <= TIME_COUNT) {
            count--
            console.log(count)
            this.sendValidationButton.title = `${count}s 后重试`
          } else {
            clearInterval(this.timer)
            this.timer = null
            this.sendValidationButton.title = '发送验证码'
            this.sendValidationButton.disabled = false
          }
        }, 1000)
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || '/' })
            this.loading = false
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg: #283443;
$light_gray: #fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0;
      -webkit-appearance: none;
      border-radius: 0;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg: #2d3a4b;
$dark_gray: #889aa4;
$light_gray: #eee;

.wrapper {
  width: 520px;
  max-width: 100%;
}

.login-container {
  min-height: 100%;
  width: 100%;
  background-color: $bg;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0 auto 40px auto;
      text-align: center;
      font-weight: bold;
    }
  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }
}
</style>
