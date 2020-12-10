import React from 'react';
import { Layout, Tag, Space } from 'antd';
import {
	PieChartOutlined,
	AppstoreOutlined,
	UsergroupAddOutlined,
	UserOutlined,
} from '@ant-design/icons';

import Logo from '../Logo/Logo';
import SideMenu from '../SideMenu/SideMenu';

import { auth } from 'Lib/auth';

import '../Layout.css';

const { Header, Content, Footer, Sider } = Layout;


const AdminLayout = (props) =>
{

	const {
		content,
	} = props;


	const menuItems = [
		{
			key: 'dashboard',
			icon: <PieChartOutlined/>,
			name: 'Dashboard',
			to: '/dashboard',
		},
		{
			key: 'boards',
			icon: <AppstoreOutlined/>,
			name: 'Boards',
			to: '/boards',
		},
		{
			key: 'users',
			icon: <UsergroupAddOutlined/>,
			name: 'Users',
			to: '/users',
		},
		{
			key: 'profile',
			icon: <UserOutlined/>,
			name: 'My Profile',
			to: '/profile',
		},
	];


	return (

		<Layout
			style={{
				height: '100vh',
			}}
		>
			<Sider
				theme='light'
				breakpoint='lg'
				collapsedWidth='0'
				onBreakpoint={broken => {
				}}
				onCollapse={(collapsed, type) => {
				}}
			>
				<Logo/>
				<SideMenu
					{...props}
					menuItems={menuItems}
					theme='light'
				/>
			</Sider>
			<Layout
				style={{
					background: '#f7f7f7',
				}}
			>
				<Header
					className='site-layout-sub-header-background'
					style={{
						padding: 0,
						textAlign: 'right'
					}}
				>
					<Space>
						Hello, {auth.getUserName()}!
						<Tag
							color='red'
							style={{ marginRight: 50, marginLeft: 10 }}
						>
							ADMIN
						</Tag>
					</Space>
				</Header>
				<Content
					style={{
						margin: '24px 16px 0',
						textAlign: 'center',
					}}
				>
					{content}
				</Content>
				<Footer
					style={{
						textAlign: 'center',
					}}
				>
					<small>
						©ETNA 2020
					</small>
				</Footer>
			</Layout>
		</Layout>
	);
}


export default AdminLayout;
