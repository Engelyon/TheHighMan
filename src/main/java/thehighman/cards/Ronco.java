package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thehighman.character.TheHighman;
import thehighman.util.CardStats;

import java.util.ArrayList;

public class Ronco extends BaseCard {
    public static final String ID = makeID("Ronco");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ENEMY,
            0
    );

    public Ronco() {
        super(ID, info);
        this.exhaust = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractMonster> validTargets = new ArrayList<>();
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                validTargets.add(mo);
            }
        }

        if (!validTargets.isEmpty()) {
            AbstractMonster target = validTargets.get(AbstractDungeon.cardRandomRng.random(validTargets.size() - 1));

            // Remove todos os poderes (buffs e debuffs)
            for (AbstractPower power : target.powers) {
                addToBot(new RemoveSpecificPowerAction(target, p, power.ID));
            }
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.target = CardTarget.ENEMY; // permite selecionar o alvo
            this.rawDescription = "Remova todos os poderes de um inimigo à sua escolha. Exaure.";
            initializeDescription();
        }
    }
}