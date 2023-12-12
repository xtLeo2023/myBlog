import { createSlice } from '@reduxjs/toolkit';

export const userSlice = createSlice({
  name: 'user',
  initialState: {
    userInfo: null,
  },
  reducers: {
    loginUser: (state, action) => {
      state.userInfo = action.payload;
    },
    // 其他 reducers...
  },
});

export const { loginUser } = userSlice.actions;
export default userSlice.reducer;
