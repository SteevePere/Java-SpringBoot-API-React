import React from 'react';
import { Space } from 'antd';


const MandatoryField = (props) =>
{

	const {
		label,
	} = props;


	return (

		<Space>
			<span
				style={{ color: 'red' }}
			>
				*
			</span>
				{label}
		</Space>
	);
}


export default MandatoryField;
