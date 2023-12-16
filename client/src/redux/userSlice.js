import { createSlice } from '@reduxjs/toolkit';

export const userSlice = createSlice({
  name: 'user',
  initialState: {
    // 从 localStorage 中获取用户信息，如果不存在则初始化为 null
    userInfo: (() => {
      const storedUserInfo = localStorage.getItem('userInfo');
      try {
        return storedUserInfo ? JSON.parse(storedUserInfo) : null;
      } catch (error) {
        console.error('解析存储的用户信息时出错：', error);
        return null;
      }
    })(),
  },
  
  reducers: {
    loginUser: (state, action) => {
      // 将登录用户的信息设置到 Redux 状态中
      state.userInfo = action.payload;
      console.log(111,action);
      // 同时将用户信息保存到 localStorage 中
      localStorage.setItem('userInfo', JSON.stringify(action.payload));
    },
    logoutUser: (state) => {
      // 将 Redux 状态中的用户信息清空为 null
      state.userInfo = null;
      
      // 同时移除 localStorage 中的用户信息
      localStorage.removeItem('userInfo');
    },
    // 其他 reducers...
  },
});

// 导出登录和注销的 action creators
export const { loginUser, logoutUser } = userSlice.actions;

// 导出 reducer
export default userSlice.reducer;


/*
  在Redux中，reducer、action和createSlice都是与状态管理相关的概念。

1. Reducer（减速器）：
    Reducer 是一个纯函数，它接收当前状态和一个 action，然后返回新的状态。
    在Redux中，所有的应用状态都由一个或多个 reducer 来管理。
    每个 reducer 负责管理全局状态树中的一部分数据。
    Reducer 应该是纯函数，即相同的输入始终产生相同的输出，不应该有副作用。
   
2. Action（动作）：
    Action 是一个描述状态变化的普通 JavaScript 对象。
    它是应用和 store 之间的数据传递的唯一途径。
    Action 对象必须包含一个 type 属性，用于描述将要执行的动作类型。
    其他属性可以根据需要自定义，用于传递数据给 reducer。

3. createSlice：
    createSlice 是由 Redux Toolkit 提供的一个函数，
    用于创建包含 reducer 和 action 的模块化切片（slice）。
    切片包含了 reducer 和相关的 action creators，可以通过 createSlice 来自动生成，减少了样板代码的编写。
    切片还可以定义初始状态（initialState），并生成一个 reducer 函数，以及包含相应 action creators 的对象。

在我的代码中，createSlice 被用于创建名为 user 的切片，其中包含了用于登录和注销的 reducer 和 action creators。
这样，你可以更方便地管理用户状态的变化，而不必手动编写大量的模板代码。

*/ 
