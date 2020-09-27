package io.github.steviebeenz.nope.modules.actions.actions;

import org.apache.commons.lang.StringUtils;
import org.bukkit.OfflinePlayer;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.actions.AbstractConditionalAction;
import io.github.steviebeenz.nope.modules.actions.Compare;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.TPSManager;

/**
 * Used for checking the server's TPS
 * 
 * @author imodm
 *
 */
public class TPSCheck extends AbstractConditionalAction {
	private int value;
	private Compare comparer;

	public TPSCheck(NOPE plugin, String data) {
		super(plugin);
		String symb = "";
		int firstNumber = "tps".length();
		for (; firstNumber < data.length(); firstNumber++) {
			String c = data.charAt(firstNumber) + "";
			if (StringUtils.isNumeric(c))
				break;
			symb += c;
		}
		comparer = Compare.fromString(symb);
		this.value = Integer.parseInt(data.substring(firstNumber));
	}

	@Override
	public boolean getValue(OfflinePlayer player, Check check) {
		return comparer.check(((Float) plugin.getModule(TPSManager.class).getTPS()).intValue(), value);
	}

}
