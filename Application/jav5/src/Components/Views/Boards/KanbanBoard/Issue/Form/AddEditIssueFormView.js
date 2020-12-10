import React from 'react';
import { Form, Input, Radio, Select } from 'antd';

import MandatoryField from 'Components/Lib/Form/MandatoryField/MandatoryField';

import { IssueTypeModel } from 'Models/IssueTypeModel';
import { IssueStatusModel } from 'Models/IssueStatusModel';
import { IssuePriorityModel } from 'Models/IssuePriorityModel';

const { TextArea } = Input;
const { Option } = Select;

const formLayout = {
	labelCol: { span: 5 },
	wrapperCol: { span: 17, offset: 2 },
};


const AddEditIssueFormView = (props) =>
{

	const {
		issue,
		milestones,
		users,
		handleInputChange,
		saveIssue,
	} = props;


	const getOptionsFromModel = (model) =>
	{
		return Object.entries(model).map(([key, value]) =>
		{
			return {
				value: value.value,
				label: value.label,
			}
		});
	}


	const getTypeOptions = () => getOptionsFromModel(IssueTypeModel);


	const getStatusOptions = () => getOptionsFromModel(IssueStatusModel);


	const getPriorityOptions = () => getOptionsFromModel(IssuePriorityModel);


	return (


		<Form
			{...formLayout}
		>
			<Form.Item
				label=<MandatoryField label='Title'/>
			>
				<Input
					value={issue.title}
					onChange={(e) => handleInputChange(e, 'title')}
					onPressEnter={saveIssue}
					autoComplete='off'
					placeholder='Input Issue Title'
				/>
			</Form.Item>
			<Form.Item
				label=<MandatoryField label='Type'/>
			>
				<Radio.Group
					options={getTypeOptions()}
					onChange={(e) => handleInputChange(e, 'type')}
					value={issue.type}
					optionType='button'
					buttonStyle='solid'
				/>
			</Form.Item>
			<Form.Item
				label=<MandatoryField label='Status'/>
			>
				<Radio.Group
					options={getStatusOptions()}
					onChange={(e) => handleInputChange(e, 'status')}
					value={issue.status}
					optionType='button'
					buttonStyle='solid'
				/>
			</Form.Item>
			<Form.Item
				label=<MandatoryField label='Priority'/>
			>
				<Radio.Group
					options={getPriorityOptions()}
					onChange={(e) => handleInputChange(e, 'priority')}
					value={issue.priority}
					optionType='button'
					buttonStyle='solid'
				/>
			</Form.Item>
			<Form.Item
				label='Description'
			>
				<TextArea
					value={issue.description}
					onChange={(e) => handleInputChange(e, 'description')}
					onPressEnter={saveIssue}
					autoComplete='off'
					placeholder='Input Issue Description'
					autoSize={{ minRows: 2, maxRows: 6 }}
					allowClear
				/>
			</Form.Item>
			<Form.Item
				label='Milestone'
			>
				<Select
					value={issue.milestoneId}
					onChange={(value) => handleInputChange(
						{target: { value: value }},
						'milestoneId'
					)}
					showSearch
					allowClear
					placeholder='Select a Milestone'
					style={{ width: '100%' }}
				>
					{milestones && milestones.map((milestone) =>
						<Option
							key={milestone.id}
							value={milestone.id}
						>
							{milestone.title}
						</Option>
					)}
					</Select>
			</Form.Item>
			<Form.Item
				label='Assignee'
			>
				<Select
					value={issue.assigneeId}
					onChange={(value) => handleInputChange(
						{target: { value: value }},
						'assigneeId'
					)}
					showSearch
					allowClear
					placeholder='Select a User'
					style={{ width: '100%' }}
				>
					{users && users.map((user) =>
						<Option
							key={user.id}
							value={user.id}
						>
							{user.username}
						</Option>
					)}
					</Select>
			</Form.Item>
		</Form>
);
}


export default AddEditIssueFormView;
