package net.koz0chka.cervusfetura.entity.custom;

import net.koz0chka.cervusfetura.entity.ModEntities;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.SharedLibrary;

import java.util.UUID;
import java.util.function.Predicate;

public class DeerEntity
        extends AnimalEntity
        implements Tameable,
        JumpingMount,
        Saddleable {
    public DeerEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }
    private static final TrackedData<Byte> DEER_FLAGS = DataTracker.registerData(DeerEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final Predicate<LivingEntity> IS_BRED_DEER = entity -> entity instanceof DeerEntity && ((AbstractHorseEntity)entity).isBred();
    private static final TargetPredicate PARENT_DEER_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0).ignoreVisibility().setPredicate(IS_BRED_DEER);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.SWEET_BERRIES);
    private static final int TAMED_FLAG = 2;
    private static final int SADDLED_FLAG = 4;
    private static final int BRED_FLAG = 8;
    private static final int EATING_GRASS_FLAG = 16;
    private static final int ANGRY_FLAG = 32;
    private static final int EATING_FLAG = 64;

    private int eatingGrassTicks;
    private int eatingTicks;
    private int angryTicks;
    public int tailWagTicks;
    public int field_6958;
    protected boolean inAir;
    protected SimpleInventory items;
    protected int temper;
    protected float jumpStrength;
    protected boolean jumping;
    private float eatingGrassAnimationProgress;
    private float lastEatingGrassAnimationProgress;
    private float angryAnimationProgress;
    private float lastAngryAnimationProgress;
    private float eatingAnimationProgress;
    private float lastEatingAnimationProgress;
    protected boolean playExtraHorseSounds = true;
    protected int soundTicks;
    @Nullable
    private UUID ownerUuid;

    public final AnimationState idlingAnimationState = new AnimationState();
    private int idleAnimationCooldown = 0;

    private void updateAnimations() {
        if (this.idleAnimationCooldown <= 0) {
            this.idleAnimationCooldown = this.random.nextInt(40) + 80;
            this.idlingAnimationState.start(this.age);
        } else {
            --this.idleAnimationCooldown;
        }
    }

    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2F);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.2));
        this.goalSelector.add(1, new AnimalMateGoal(this, 1.15D));
        this.goalSelector.add(2, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.SWEET_BERRIES), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.0));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.7));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createDeerAttributes() {
        return MobEntity.createMobAttributes()
                .add (EntityAttributes.GENERIC_MAX_HEALTH, 15)
                .add (EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f)
                .add (EntityAttributes.GENERIC_ARMOR, 0.5f)
                .add (EntityAttributes.GENERIC_ATTACK_DAMAGE, 2);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DEER_FLAGS, (byte)0);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.SWEET_BERRIES);
    }

    protected boolean getDeerFlag(int bitmask) {
        return (this.dataTracker.get(DEER_FLAGS) & bitmask) != 0;
    }

    protected void setDeerFlag(int bitmask, boolean flag) {
        byte b = this.dataTracker.get(DEER_FLAGS);
        if (flag) {
            this.dataTracker.set(DEER_FLAGS, (byte)(b | bitmask));
        } else {
            this.dataTracker.set(DEER_FLAGS, (byte)(b & ~bitmask));
        }
    }

    public boolean isTame() {
        return this.getDeerFlag(TAMED_FLAG);
    }

    @Override
    @Nullable
    public UUID getOwnerUuid() {
        return this.ownerUuid;
    }

    @Override
    public EntityView method_48926() {
        return null;
    }

    public void setOwnerUuid(@Nullable UUID ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    public boolean isInAir() {
        return this.inAir;
    }

    public void setTame(boolean tame) {
        this.setDeerFlag(TAMED_FLAG, tame);
    }

    public void setInAir(boolean inAir) {
        this.inAir = inAir;
    }

    public void setEatingGrass(boolean eatingGrass) {
        this.setDeerFlag(EATING_GRASS_FLAG, eatingGrass);
    }

    @Override
    protected void updateForLeashLength(float leashLength) {
        if (leashLength > 6.0f && this.isEatingGrass()) {
            this.setEatingGrass(false);
        }
    }

    public boolean isEatingGrass() {
        return this.getDeerFlag(EATING_GRASS_FLAG);
    }

    public boolean isAngry() {
        return this.getDeerFlag(ANGRY_FLAG);
    }

    public boolean isBred() {
        return this.getDeerFlag(BRED_FLAG);
    }

    public void setBred(boolean bred) {
        this.setDeerFlag(BRED_FLAG, bred);
    }

    @Override
    public boolean canBeSaddled() {
        return this.isAlive() && !this.isBaby() && this.isTame();
    }

    @Override
    public void saddle(@Nullable SoundCategory sound) {
        this.items.setStack(0, new ItemStack(Items.SADDLE));
    }

    @Override
    public boolean isSaddled() {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.DEER.create(world);
    }

    @Override
    public void setJumpStrength(int strength) {

    }

    @Override
    public boolean canJump() {
        return false;
    }

    @Override
    public void startJumping(int height) {

    }

    @Override
    public void stopJumping() {

    }

    protected void walkToParent() {
        DeerEntity livingEntity;
        if (this.isBred() && this.isBaby() && !this.isEatingGrass() && (livingEntity = this.getWorld().getClosestEntity(DeerEntity.class, PARENT_DEER_PREDICATE, this, this.getX(), this.getY(), this.getZ(), this.getBoundingBox().expand(16.0))) != null && this.squaredDistanceTo(livingEntity) > 4.0) {
            this.navigation.findPathTo(livingEntity, 0);
        }
    }

    public boolean eatsGrass() {
        return true;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.getWorld().isClient || !this.isAlive()) {
            return;
        }
        if (this.random.nextInt(900) == 0 && this.deathTime == 0) {
            this.heal(1.0f);
        }
        if (this.eatsGrass()) {
            if (!this.isEatingGrass() && !this.hasPassengers() && this.random.nextInt(300) == 0 && this.getWorld().getBlockState(this.getBlockPos().down()).isOf(Blocks.GRASS_BLOCK)) {
                this.setEatingGrass(true);
            }
            if (this.isEatingGrass() && ++this.eatingGrassTicks > 50) {
                this.eatingGrassTicks = 0;
                this.setEatingGrass(false);
            }
        }
        this.walkToParent();
    }

    private void setEating() {
        if (!this.getWorld().isClient) {
            this.eatingTicks = 1;
            this.setDeerFlag(EATING_FLAG, true);
        }
    }
}
