import React, { useState } from 'react';
import { Form, Input, Button, Menu, Card } from 'antd';
import { useNavigate } from 'react-router-dom';
import {doUser} from './api/doUser'
import { useDispatch} from 'react-redux';
import { loginUser } from './redux/userSlice.js';
import './App.css';

const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [formType, setFormType] = useState('login');

  const handleClick = (e) => {
    setFormType(e.key);
  };

  const menuItems = [
    {
      label: '登录',
      key: 'login',
    },
    {
      label: '注册',
      key: 'register',
    },
  ];
  const onFinish = async (values) => {
    console.log('Received values of form: ', values);
    
    let p = {};
    p.action = formType;
    p.data = values;
    let rs = await doUser(p);
    console.log(rs)
    if (rs.data.trim() === 'success') {
      dispatch(loginUser(values));
      navigate('/blog/home');
    }
    
  };

  return (
    
    <div style={{ 
      display: 'flex', 
      justifyContent: 'center', 
      alignItems: 'center', 
      height: '100vh' // 视口高度的100%
    }}>
      <Card style={{ width: 300, margin: '100px auto' }}>
        <Menu 
          onClick={handleClick} 
          selectedKeys={[formType]} 
          mode="horizontal"
          items={menuItems}
          style={{ marginBottom: 20, border: 'none' }}
        />

        <Form
          name="userForm"
          onFinish={onFinish}
          layout="vertical"
        >
          {formType === 'register' && (
            <Form.Item
              name="email"
              rules={[{ required: true, message: '请输入邮箱!' },{ type: 'email', message: '请输入有效的邮箱地址!' }]}
            >
              <Input placeholder="邮箱" />
            </Form.Item>
          )}

          <Form.Item
            name="username"
            rules={[{ required: true, message: '请输入用户名!' }]}
          >
            <Input placeholder="用户名" />
          </Form.Item>

          <Form.Item
            name="password"
            rules={[{ required: true, message: '请输入密码!' }]}
          >
            <Input.Password placeholder="密码" />
          </Form.Item>

          {formType === 'register' && (
            <Form.Item
              name="confirm"
              dependencies={['password']}
              hasFeedback
              rules={[
                { required: true, message: '请确认密码!' },
                ({ getFieldValue }) => ({
                  validator(_, value) {
                    if (!value || getFieldValue('password') === value) {
                      return Promise.resolve();
                    }
                    return Promise.reject(new Error('两次输入的密码不一致!'));
                  },
                }),
              ]}
            >
              <Input.Password placeholder="确认密码" />
            </Form.Item>
          )}

          <Form.Item>
            <Button type="primary" htmlType="submit" block>
              {formType === 'login' ? '登录' : '注册'}
            </Button>
          </Form.Item>
        </Form>
      </Card>
    </div>
  );
};

export default Login;
