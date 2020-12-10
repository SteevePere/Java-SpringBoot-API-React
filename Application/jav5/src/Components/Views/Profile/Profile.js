import React from 'react';
import { NavLink } from 'react-router-dom';
import { Row, Col, Card, Button, Divider } from 'antd';
import {
	EditOutlined,
} from '@ant-design/icons';

import ProfilePicture from './ProfilePicture/ProfilePicture';
import ProfileDetails from './ProfileDetails/ProfileDetails';

import { auth } from 'Lib/auth';


const Profile = (props) =>
{

	const user = auth.getUser();
	const isAdmin = auth.isAdmin();


	return (

		<Row style={{ width: '100%' }}>
			<Card style={{ width: '100%' }}>
				<Row
					type='flex'
					gutter={[24, 24]}
				>
					<Col
						xs={24}
						sm={24}
						md={24}
						lg={8}
						xl={8}
					>
						<Card>
							<ProfilePicture
								user={user}
								isAdmin={isAdmin}
							/>
						</Card>
					</Col>
					<Col
						xs={24}
						sm={24}
						md={24}
						lg={16}
						xl={16}
					>
						<Card style={{ textAlign: 'left' }}>
							<ProfileDetails
								user={user}
								isAdmin={isAdmin}
							/>
						</Card>
						<Divider/>
						<NavLink to='/users'>
							<Button
								type='primary'
								block
							>
								<EditOutlined/>
								Edit my Profile
							</Button>
						</NavLink>
					</Col>
				</Row>
			</Card>
		</Row>
	);
}


export default Profile;
