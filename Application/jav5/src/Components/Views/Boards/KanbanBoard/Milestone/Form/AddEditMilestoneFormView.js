import React from 'react';
import { Form, Input, DatePicker } from 'antd';
import moment from 'moment';

import MandatoryField from 'Components/Lib/Form/MandatoryField/MandatoryField';

const { TextArea } = Input;

const formLayout = {
	labelCol: { span: 5 },
	wrapperCol: { span: 17, offset: 2 },
};


const AddEditMilestoneFormView = (props) =>
{

	const {
		milestone,
		handleInputChange,
		handleDatePickerChange,
		saveMilestone,
	} = props;


	return (


		<Form
			{...formLayout}
		>
			<Form.Item
				label=<MandatoryField label='Title'/>
			>
				<Input
					value={milestone.title}
					onChange={(e) => handleInputChange(e, 'title')}
					onPressEnter={saveMilestone}
					autoComplete='off'
					placeholder='Input Milestone Title - 70 characters max'
					maxLength={70}
				/>
			</Form.Item>
			<Form.Item
				label='Description'
			>
				<TextArea
					value={milestone.description}
					onChange={(e) => handleInputChange(e, 'description')}
					onPressEnter={saveMilestone}
					autoComplete='off'
					placeholder='Input Milestone Description - 140 characters max'
					autoSize={{ minRows: 2, maxRows: 6 }}
					maxLength={140}
					allowClear
				/>
			</Form.Item>
			<Form.Item
				label='Due Date'
			>
				<DatePicker
					value={milestone.dueDate ? moment(milestone.dueDate) : undefined}
					onChange={handleDatePickerChange}
					style={{
						width: '100%'
					}}
					placeholder='Select Due Date'
					allowClear
				/>
			</Form.Item>
		</Form>
);
}


export default AddEditMilestoneFormView;
