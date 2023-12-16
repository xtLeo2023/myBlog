import React, { useEffect, useState } from 'react';
import { Card, Row, Col, Button, Typography, Input } from 'antd';
import { useSelector } from 'react-redux';
import a001 from '../images/001.png';
import { doSQL } from '../api/doSQL';

const { Title, Paragraph } = Typography;
const { TextArea } = Input;

const Home = () => {
  const [items, setItems] = useState([]);
  const [selectedItem, setSelectedItem] = useState(null);
  const [editedContent, setEditedContent] = useState('');

  const user = useSelector(state => state.user.userInfo);
  // 计算当前用户发表的博客数量
  const userBlogCount = items.filter(item => item.userId === user.username).length;

  useEffect(() => {
    loaddata();
  }, []);

  const loaddata = async () => {
    let p = {};
    p.sql = "select * from blog order by posttime desc";
    let rs = await doSQL(p);
    setItems(rs.data);
  };

  const handleViewDetails = (item) => {
    setSelectedItem(item);
    console.log(item);
    console.log(user);
    setEditedContent(item.content); // 初始化编辑内容为当前博客内容
  };

  const handleCloseDetails = () => {
    setSelectedItem(null);
  };

  const handleDeleteBlog = async ()=> {
    let p={};
    p.update='true';
    p.sql="delete from blog where blogid="+selectedItem.blogId;
    console.log(selectedItem,p)
    let rs=await doSQL(p);
    console.log(222,rs)
    if(rs.data.trim() === 'success'){
      loaddata();
      setSelectedItem(null)
    }
  }

  const handleSaveChanges = async () => {
    // 实现保存修改的逻辑
    // 需要更新数据库中的博客内容，并重新加载数据
    // 例如：
    let p = {};
    p.update = 'true';
    p.sql = `update blog set content='${editedContent}' where blogid=${selectedItem.blogId}`;
    let rs = await doSQL(p);
    if (rs.data.trim() === 'success') {
      loaddata();
      setSelectedItem(null);
    }
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center' }}>
      <div style={{ width: 1200 }}>
        <Title level={3} style={{ marginBottom: 24 }}>首页</Title>
        <Row gutter={16}>
          <Col span={3}>
            <Card
              style={{ width: 140, height: 220, textAlign:'center' }}
              cover={<img alt="example" src={a001} />}
            >
              <Card.Meta 
                title={user.username} 
                description={`博客数: ${userBlogCount}条`} 
              />

            </Card>
          </Col>
          <Col span={20} style={{overflow:'auto'}}>
          {selectedItem ? (
            <div>
              <Card style={{ marginBottom: 16, textAlign: 'center', height: 550 }}>
                <h3>{selectedItem.title}</h3>
                <h4>{selectedItem.userId}</h4>
                <p>{selectedItem.postTime}</p>
                {selectedItem.userId === user.username ? (
                  <TextArea 
                    value={editedContent} 
                    onChange={e => setEditedContent(e.target.value)} 
                    style={{ textAlign: 'left', height: 300 }}
                  />
                ) : (
                  <Paragraph style={{ textAlign: 'left' }}>
                    {selectedItem.content}
                  </Paragraph>
                )}
              </Card>
              <div style={{ textAlign: 'right' }}>
                {selectedItem.userId === user.username && (
                  <>
                    <Button type="primary" onClick={handleSaveChanges} style={{ marginRight: 16 }}>保存修改</Button>
                    <Button type="primary" danger onClick={handleDeleteBlog} style={{ marginRight: 16 }}>删除博客</Button>
                  </>
                )}
                <Button type="primary" onClick={handleCloseDetails}>返回主页</Button>
              </div>
            </div>
          ) : (
            items.map(item => (
              <Card key={item.blogid} style={{ marginBottom: 16 }}>
                <Row justify="space-between" align="middle">
                  <Col span={20}>
                    <h3>{item.title}</h3>
                    <h4 style={{textIndent:16}}>  {item.userId}</h4>
                    <p style={{
                      display: '-webkit-box',
                      WebkitBoxOrient: 'vertical',
                      WebkitLineClamp: 2,
                      overflow: 'hidden',
                      textOverflow: 'ellipsis'
                    }}>
                      {item.content}
                    </p>
                    <p>{item.postTime}</p>
                  </Col>
                  <Col span={3}>
                    <Button type="primary" onClick={() => handleViewDetails(item)}>查看详情</Button>
                  </Col>
                </Row>
              </Card>
            ))
          )}
          </Col>
        </Row>
      </div>
    </div>
  );
};

export default Home;
