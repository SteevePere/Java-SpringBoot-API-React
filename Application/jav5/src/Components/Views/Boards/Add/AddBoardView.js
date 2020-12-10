import React from 'react';
import { Button, Modal, Space, Card, Form, Input } from 'antd';
import {
	PlusOutlined,
	AppstoreOutlined,
} from '@ant-design/icons';

import MandatoryField from 'Components/Lib/Form/MandatoryField/MandatoryField';

const formLayout = {
	labelCol: { span: 5 },
	wrapperCol: { span: 17, offset: 2 },
};


const AddBoardView = (props) =>
{

	const {
		board,
		openModal,
		closeModal,
		modalVisible,
		handleInputChange,
		createBoard,
	} = props;


	return (

		<>
			<Button
				onClick={openModal}
				type='primary'
			>
				<PlusOutlined/>
				Create New Board
			</Button>
			<Modal
				title={<Space><AppstoreOutlined/> New Board</Space>}
				visible={modalVisible}
				onOk={createBoard}
				onCancel={closeModal}
				width={800}
			>
				<Card>
					<Form
						{...formLayout}
					>
						<Form.Item
							label=<MandatoryField label='Title'/>
						>
							<Input
								value={board.title}
								onChange={(e) => handleInputChange(e, 'title')}
								onPressEnter={createBoard}
								autoComplete='off'
								placeholder='Input Title'
							/>
						</Form.Item>
					</Form>
				</Card>
			</Modal>
		</>
	);
}


export default AddBoardView;
