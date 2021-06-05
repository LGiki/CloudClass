<template>
  <div class="container">
    <div class="login-container">
      <div class="title-container">
        <h2 class="title">到云APP 后台管理系统</h2>
      </div>
      <div style="display: flex; justify-content: center">
        <el-radio-group v-model="loginType" style="margin-bottom: 30px;">
          <el-radio-button label="password">手机号/密码登录</el-radio-button>
          <el-radio-button label="sms">短信验证码登录</el-radio-button>
        </el-radio-group>
      </div>
      <template v-if="loginType === 'password'">
        <el-form
          ref="passwordLoginForm"
          :model="passwordLoginForm"
          :rules="passwordLoginRules"
          class="login-form"
          auto-complete="on"
          label-position="left"
        >
          <el-form-item prop="username">
            <span class="svg-container">
              <svg-icon icon-class="user"/>
            </span>
            <el-input
              ref="username"
              v-model="passwordLoginForm.username"
              placeholder="用户名"
              name="username"
              type="text"
              tabindex="1"
              auto-complete="on"
            />
          </el-form-item>

          <el-form-item prop="password">
            <span class="svg-container">
              <svg-icon icon-class="password"/>
            </span>
            <el-input
              :key="passwordType"
              ref="password"
              v-model="passwordLoginForm.password"
              :type="passwordType"
              placeholder="密码"
              name="password"
              tabindex="2"
              auto-complete="on"
              @keyup.enter.native="handlePasswordLogin"
            />
            <span class="show-pwd" @click="showPwd">
              <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
            </span>
          </el-form-item>
          <div style="display: flex; justify-content: flex-end">
            <!--            <el-button type="text" @click="$router.push('/signup')">注册</el-button>-->
            <el-button type="text" @click="$router.push('/reset-password')">找回密码</el-button>
          </div>
          <el-button
            :loading="loading"
            type="primary"
            style="width:100%;margin-bottom:30px;"
            @click.native.prevent="handlePasswordLogin"
          >登录
          </el-button>
        </el-form>
      </template>
      <template v-else>
        <el-form
          ref="smsLoginForm"
          :model="smsLoginForm"
          :rules="smsLoginRules"
          class="login-form"
          auto-complete="on"
          label-position="left"
        >
          <div style="width: 100%;">
            <el-form-item prop="phone">
              <span class="svg-container">
                <svg-icon icon-class="user"/>
              </span>
              <el-input
                key="phone"
                ref="phone"
                v-model="smsLoginForm.phone"
                placeholder="手机号"
                name="phone"
                tabindex="2"
                auto-complete="on"
                :maxlength="13"
              />
            </el-form-item>
          </div>
          <div style="width: 100%;display: inline-grid;grid-template-columns: 3fr 1fr;grid-column-gap: 20px;">
            <el-form-item prop="verifyCode">
              <span class="svg-container">
                <svg-icon icon-class="phone"/>
              </span>
              <el-input
                key="verifyCode"
                ref="verifyCode"
                v-model="smsLoginForm.verifyCode"
                type="number"
                placeholder="验证码"
                name="verifyCode"
                tabindex="2"
                auto-complete="on"
                @keyup.enter.native="handleSMSLogin"
              />
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
          <div style="display: flex; justify-content: flex-end">
            <!--            <el-button type="text" @click="$router.push('/signup')">注册</el-button>-->
            <el-button type="text" @click="$router.push('/reset-password')">找回密码</el-button>
          </div>
          <el-button
            :loading="loading"
            type="primary"
            style="width:100%;margin-bottom:30px;"
            @click.native.prevent="handleSMSLogin"
          >登录
          </el-button>
        </el-form>
      </template>
    </div>
  </div>
</template>

<script>
import md5 from 'md5'
import {loginByPassword, loginBySMS, sendSMS} from '@/api/user'
import {validatePhone} from '@/utils/validate'

export default {
  name: 'Login',
  data() {
    const validateUsername = (rule, value, callback) => {
      this.$emit('')
      if (value.length === 0) {
        callback(new Error('用户名不能为空'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length === 0) {
        callback(new Error('密码不能为空'))
      } else {
        callback()
      }
    }
    const validateVerifyCode = (rule, value, callback) => {
      if (value.length === 0) {
        callback(new Error('短信验证码不能为空'))
      } else {
        callback()
      }
    }
    return {
      passwordLoginForm: {
        username: '',
        password: ''
      },
      smsLoginForm: {
        phone: '',
        verifyCode: ''
      },
      loginType: 'password',
      passwordLoginRules: {
        username: [{required: true, trigger: 'blur', validator: validateUsername}],
        password: [{required: true, trigger: 'blur', validator: validatePassword}]
      },
      smsLoginRules: {
        phone: [{required: true, trigger: 'blur', validator: validatePhone}],
        verifyCode: [{required: true, trigger: 'blur', validator: validateVerifyCode}]
      },
      sendValidationButton: {
        title: '发送验证码',
        disabled: false
      },
      loading: false,
      passwordType: 'password',
      redirect: undefined,
      countDownIntervalId: null
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  destroyed() {
    this.clearCountDown()
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    startCountDown() {
      this.sendValidationButton.disabled = true
      const TIME_COUNT = 60
      this.sendValidationButton.title = `${TIME_COUNT}s 后重试`
      if (!this.countDownIntervalId) {
        let count = TIME_COUNT
        this.countDownIntervalId = setInterval(() => {
          if (count > 0 && count <= TIME_COUNT) {
            count--
            this.sendValidationButton.title = `${count}s 后重试`
          } else {
            this.clearCountDown()
          }
        }, 1000)
      }
    },
    clearCountDown() {
      if (this.countDownIntervalId) {
        clearInterval(this.countDownIntervalId)
        this.countDownIntervalId = null
        this.sendValidationButton.title = '发送验证码'
        this.sendValidationButton.disabled = false
      }
    },
    handleSendValidationCode() {
      sendSMS(this.smsLoginForm.phone).then(res => {
        if (res.data.code === '200') {
          this.startCountDown()
        } else {
          this.$message.error(res.data.msg)
        }
      }).catch(err => {
        console.log(err)
        this.$message.error('发送验证码失败')
      })
    },
    handleSMSLogin() {
      this.$refs.smsLoginForm.validate(valid => {
        if (valid) {
          this.loading = true
          loginBySMS(this.smsLoginForm.phone.trim(), this.smsLoginForm.verifyCode.trim()).then(res => {
            this.loading = false
            if (res.data.code === '200') {
              this.$store.commit('token/SET_TOKEN', res.data.token)
              this.$router.push({path: this.redirect || '/'})
            } else {
              this.$message.error(res.data.msg)
            }
          }).catch(err => {
            this.loading = false
            console.log(err)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    handlePasswordLogin() {
      this.$refs.passwordLoginForm.validate(valid => {
        if (valid) {
          this.loading = true
          const passwordMD5 = md5(this.passwordLoginForm.password).slice(8, 24)
          loginByPassword(this.passwordLoginForm.username.trim(), passwordMD5).then(res => {
            this.loading = false
            if (res.data.code === '200') {
              this.$store.commit('token/SET_TOKEN', res.data.token)
              this.$router.push({path: this.redirect || '/'})
            } else {
              this.$message.error(res.data.msg)
            }
          }).catch(err => {
            this.loading = false
            console.log(err)
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

.container {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  //background-color: $bg;
  background: url("../../assets/login-background.jpg") no-repeat;
  background-size: cover;
}

/* reset element-ui css */
.login-container {
  backdrop-filter: blur(35px) saturate(1.9);
  background-color: rgba(255, 255, 255, .2);
  border-radius: 25px;
  padding: 25px;
  box-shadow:
    0 0.8px 4.7px rgba(0, 0, 0, 0.028),
    0 2px 9.9px rgba(0, 0, 0, 0.04),
    0 3.8px 16.2px rgba(0, 0, 0, 0.05),
    0 6.7px 25.2px rgba(0, 0, 0, 0.06),
    0 12.5px 41.1px rgba(0, 0, 0, 0.072),
    0 30px 86px rgba(0, 0, 0, 0.1)
;


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

.login-container {
  //min-height: 100%;
  //width: 100%;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    //padding: 160px 35px 0;
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
      font-size: 2.5em;
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
