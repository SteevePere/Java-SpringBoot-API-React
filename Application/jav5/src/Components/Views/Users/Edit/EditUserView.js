import React from 'react';
import { Row, Card, Form, Input, Select,
	Divider, Button, Drawer, Space } from 'antd';
import {
	SaveOutlined,
	EditOutlined,
	InfoCircleOutlined,
} from '@ant-design/icons';

import MandatoryField from 'Components/Lib/Form/MandatoryField/MandatoryField';

import { RolesModel } from 'Models/RolesModel';

const formLayout = {
	labelCol: { span: 5 },
	wrapperCol: { span: 17, offset: 2 },
};

const { Option } = Select;


const EditUserView = (props) =>
{

	const {
		userToEdit,
		isMe,
		canChangeRole,
		drawerVisible,
		setDrawerVisible,
		handleInputChange,
		updateUser,
	} = props;


	const warning = (
		<i>
			<InfoCircleOutlined style={{ marginRight: 3 }}/>
			You are currently updating your own profile.
			You will need to sign in again after saving.
		</i>
	);


	return (

		<Drawer
			title=''
			placement='right'
			closable
			destroyOnClose
			visible={drawerVisible}
			onClose={() => setDrawerVisible(false)}
			width={800}
		>
			{userToEdit &&
			<Row
				type='flex'
				justify='center'
				align='middle'
			>
				<Card
					title={<Space><EditOutlined/> Edit User</Space>}
					style={{ width: '100%' }}
				>
					<Form
						{...formLayout}
					>
						{canChangeRole() &&
						<Form.Item
							label={<MandatoryField label='Role'/>}
						>
							<Select
								showSearch
								placeholder='Select a Role'
								value={userToEdit.role}
								style={{ width: '100%' }}
								onChange={(value) => handleInputChange(
									{target: { value: value }},
									'role'
								)}
							>
							{Object.keys(RolesModel).map((key) =>
								<Option
									key={RolesModel[key]}
									value={RolesModel[key]}
								>
									{key}
								</Option>
							)}
							</Select>
						</Form.Item>}
						<Form.Item
							label={<MandatoryField label='Username'/>}
							rules={[{ required: true }]}
							hasFeedback
						>
							<Input
								value={userToEdit.username}
								onChange={(e) => handleInputChange(e, 'username')}
								autoComplete='off'
							/>
						</Form.Item>
						<Form.Item
							label='Password'
							hasFeedback
						>
							<Input.Password
								value={userToEdit.password}
								onChange={(e) => handleInputChange(e, 'password')}
								autoComplete='off'
							/>
						</Form.Item>
						<Form.Item
							label={userToEdit.password ?
								<MandatoryField label='Confirm Password'/> :
								'Confirm Password'
							}
							hasFeedback
						>
							<Input.Password
								value={userToEdit.confirmPassword}
								onChange={(e) => handleInputChange(e, 'confirmPassword')}
								autoComplete='off'
							/>
						</Form.Item>
					</Form>
					<Divider/>
					<Form.Item>
						<Button
							block
							type='primary'
							onClick={updateUser}
						>
							<SaveOutlined/>
							Save Changes
						</Button>
					</Form.Item>
					{isMe(userToEdit) && warning}
				</Card>
			</Row>}
		</Drawer>
	);
}


export default EditUserView;
