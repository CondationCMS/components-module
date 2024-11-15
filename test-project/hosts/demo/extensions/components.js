import { $hooks } from 'system/hooks.mjs';

$hooks.registerAction("components/test-hook", (context) => {
	return `Hello ${context.arguments().get("name")}, this is a custom component!`
})