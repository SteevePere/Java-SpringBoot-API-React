import { notification } from 'antd';


export const utils =
{

	openNotification(type, message, description, placement, duration = 1.5)
	{
		notification[type]({
			message: message,
			description: description,
			placement: placement,
			duration: duration
		});
	},
};
