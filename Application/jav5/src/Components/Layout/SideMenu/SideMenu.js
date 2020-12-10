import React from 'react';
import { NavLink, useLocation } from 'react-router-dom';
import { Menu, Divider } from 'antd';
import {
	LogoutOutlined,
} from '@ant-design/icons';

import Logout from 'Components/Auth/Logout/Logout';


const SideMenu = (props) =>
{

	const {
		menuItems,
		theme,
	} = props;


	const location = useLocation();


	const getCurrentRoute = () =>
	{
		let currentRoute = location && location.pathname &&
			location.pathname.replace('/', '');

		if (currentRoute && currentRoute.includes('/'))
			currentRoute = currentRoute.substring(0, currentRoute.indexOf('/'));

		return currentRoute;
	}


	return (

		<Menu
			mode='inline'
			theme={theme}
			selectedKeys={[getCurrentRoute()]}
			style={{ marginTop: 20 }}
		>
			{menuItems && menuItems.map(menuItem =>
				<Menu.Item
					key={menuItem.key}
					icon={menuItem.icon}
				>
					<NavLink to={menuItem.to}>
						{menuItem.name}
					</NavLink>
				</Menu.Item>
			)}
			<Divider/>
			<Menu.Item
				key='signout'
				icon={<LogoutOutlined/>}
			>
				<Logout
					{...props}
				/>
			</Menu.Item>
		</Menu>
	);
}


export default SideMenu;
