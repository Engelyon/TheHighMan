package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.VisaoDistorcidaPower;
import thehighman.powers.Chapado;
import thehighman.util.CardStats;

public class VisaoDistorcida extends BaseCard {
    public static final String ID = makeID("VisaoDistorcida");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            0
    );

    private static final int CHAPADO_AMOUNT = 5;
    private static final int SELF_DAMAGE = 10;

    public VisaoDistorcida() {
        super(ID, info);
        this.selfRetain = true;
        this.exhaust = true;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Aplica 5 de Chapado a todos os inimigos
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if (!mo.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(mo, p, new Chapado(mo, CHAPADO_AMOUNT), CHAPADO_AMOUNT));
            }
        }

        // Causa 10 de dano ao jogador
        addToBot(new LoseHPAction(p, p, SELF_DAMAGE));

        // Aplica o efeito para o próximo turno
        addToBot(new ApplyPowerAction(p, p, new VisaoDistorcidaPower(p)));
    }
}