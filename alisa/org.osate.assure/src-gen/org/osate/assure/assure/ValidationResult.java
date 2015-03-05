/**
 */
package org.osate.assure.assure;

import org.osate.verify.verify.VerificationValidation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Validation Result</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.osate.assure.assure.ValidationResult#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.osate.assure.assure.AssurePackage#getValidationResult()
 * @model
 * @generated
 */
public interface ValidationResult extends VerificationResult
{
  /**
   * Returns the value of the '<em><b>Target</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Target</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Target</em>' reference.
   * @see #setTarget(VerificationValidation)
   * @see org.osate.assure.assure.AssurePackage#getValidationResult_Target()
   * @model
   * @generated
   */
  VerificationValidation getTarget();

  /**
   * Sets the value of the '{@link org.osate.assure.assure.ValidationResult#getTarget <em>Target</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Target</em>' reference.
   * @see #getTarget()
   * @generated
   */
  void setTarget(VerificationValidation value);

} // ValidationResult